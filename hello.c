#include <stdint.h>
#include <stdbool.h>
#include <stdlib.h>
#include "tm4c123gh6pm.h"
#include "hw_memmap.h"
#include "hw_types.h"
#include "hw_timer.h"
#include "hw_gpio.h"
#include "debug.h"
#include "sysctl.h"
#include "interrupt.h"
#include "timer.h"
#include "gpio.h"
#include "pin_map.h"
#include "rom.h"
#include "uart.h"

volatile bool triggerOn = 1;
volatile bool currentStatus = true;

void Timer0AInterupt(void);
void PortAInterupt(void);
void SendATCommand(char*);
void transmit();


/*wiring: HC-SR04   Tiva
 *          Vcc  -> Vbus
            GND  -> GND
            Trig -> PA3
            Echo -> PA2

          ESP-01  Tiva
            Rx   -> PC5
            Tx   -> PC4
            3V3  -> 3.3V   3V3 and EN should be connected to the 3.3V rail of the power supply to limit the strain on the tiv
            EN   -> 3.3V   to limit the strain on the Tiva's 3.3V regulator
            GND  -> GND
*/

int main(void){
    // Set the System clock to 40MHz
    SysCtlClockSet(SYSCTL_SYSDIV_5 | SYSCTL_USE_PLL | SYSCTL_OSC_MAIN | SYSCTL_XTAL_16MHZ);
    // Enable the clock for peripherals PortA, Timer0, TIMER1
    SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOA);
    SysCtlPeripheralEnable(SYSCTL_PERIPH_TIMER0);
    SysCtlPeripheralEnable(SYSCTL_PERIPH_TIMER1);
    SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOF);
    SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOC);
    GPIOPinTypeGPIOOutput(GPIO_PORTF_BASE, GPIO_PIN_1|GPIO_PIN_2|GPIO_PIN_3);
    GPIOPinTypeGPIOOutput(GPIO_PORTC_BASE, GPIO_PIN_4);

    //enable UART
    SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOC); //enable GPIO port C
    SysCtlPeripheralEnable(SYSCTL_PERIPH_UART4); //enable UART4
    while(!SysCtlPeripheralReady(SYSCTL_PERIPH_UART4));
    GPIOPinConfigure(GPIO_PC4_U4RX);//pin PC4  is UART4 RX
    GPIOPinConfigure(GPIO_PC5_U4TX);//pin PC5 is UART4 TX
    GPIOPinTypeUART(GPIO_PORTC_BASE, GPIO_PIN_4 | GPIO_PIN_5);//Set UART in
    UARTConfigSetExpClk(UART4_BASE, SysCtlClockGet(), 9600, (UART_CONFIG_WLEN_8 | UART_CONFIG_STOP_ONE | UART_CONFIG_PAR_NONE)); //set up UART clock, baud rate = 9600


    // enable all interrupts
    IntMasterEnable();


    // Set the PA3 as Output
    GPIOPinTypeGPIOOutput(GPIO_PORTA_BASE, GPIO_PIN_3);
    // Set the PA2 as Input
    GPIOPinTypeGPIOInput(GPIO_PORTA_BASE, GPIO_PIN_2);
    GPIOPadConfigSet(GPIO_PORTA_BASE, GPIO_PIN_2, GPIO_STRENGTH_8MA, GPIO_PIN_TYPE_STD_WPD);
    //enable the Interrupt for PA2
    IntEnable(INT_GPIOA);
    GPIOIntTypeSet(GPIO_PORTA_BASE, GPIO_PIN_2, GPIO_BOTH_EDGES);
    GPIOIntEnable(GPIO_PORTA_BASE, GPIO_INT_PIN_2);

    // Configure Timer0
    TimerConfigure(TIMER0_BASE, TIMER_CFG_ONE_SHOT);
    // Enable Interrupt vector Timer0A
    IntEnable(INT_TIMER0A);
    // Enables timer interrupt
    TimerIntEnable(TIMER0_BASE, TIMER_TIMA_TIMEOUT);

    // Configure TIMER1
    TimerConfigure(TIMER1_BASE, TIMER_CFG_ONE_SHOT_UP);

    SendATCommand("AT+RST"); //reset the wifi module to run the startup sequence
    SysCtlDelay((SysCtlClockGet() * 5) / 3); //5 second delay to let the the esp go through the startup sequence
    SendATCommand("AT+CWJAP=\"PinkGiraffe\",\"M0b4$her65\""); //connect to a wifi network using ssid and password. The ESP-01 will save the networks information and automatically reconnect on startup
    //so sending this command is not necessary after is has already connected to the network
    SysCtlDelay((SysCtlClockGet() * 5) / 3);//5 sec delay to allow the connection to the network

    SendATCommand("AT+CWMODE=3"); // Sets the ESP as a client
    SysCtlDelay((SysCtlClockGet() * 2) / 3); //delays are to give the esp to execute the commands
    SendATCommand("AT+CIPMUX=0"); // Set up multiple connections
    SysCtlDelay((SysCtlClockGet() * 2) / 3);
  //  SendATCommand("AT+CIPSTART=\"TCP\",\"99.233.228.181\",27015"); //connect to server at a certain IP address and port
   // SendATCommand("AT+CIPSTART=\"TCP\",\"99.251.21.107\",3306"); //connect to server at a certain IP address and port

  //  SendATCommand("AT+CIPSTART=\"TCP\",\"192.168.0.37\",3306"); //connect to server at a certain IP address and port

    SysCtlDelay((SysCtlClockGet() * 2) / 3);



    while (1){
        if (triggerOn){
            // Load 10us delay
            TimerLoadSet(TIMER0_BASE, TIMER_A, (SysCtlClockGet() / 100000) -1);
            // Make the PA3 High
            GPIOPinWrite(GPIO_PORTA_BASE, GPIO_PIN_3, GPIO_PIN_3);
            // interrupt when timer is up
            TimerEnable(TIMER0_BASE, TIMER_A);

            triggerOn = false;
        }
    }
}


void Timer0AInterupt(void){

    // Clear the timer interrupt
    TimerIntClear(TIMER0_BASE, TIMER_TIMA_TIMEOUT);
    // Disable the timer
    TimerDisable(TIMER0_BASE, TIMER_A);
    // Make the PA3 Low
    GPIOPinWrite(GPIO_PORTA_BASE, GPIO_PIN_3, 0x00);
}

void PortAInterupt(void){
    volatile uint32_t distance = 0;
    volatile uint32_t echoTime = 0;

    // Clear the GPIO Interrupt
    GPIOIntClear(GPIO_PORTA_BASE , GPIO_INT_PIN_2);

    // when PA2 goes high
    if (GPIOPinRead(GPIO_PORTA_BASE, GPIO_PIN_2) == GPIO_PIN_2){
        // Initialize TIMER1 with value 0
        HWREG(TIMER1_BASE + TIMER_O_TAV) = 0;
        // Enable TIMER1 to start measuring duration for which Echo Pin is High
        TimerEnable(TIMER1_BASE, TIMER_A);
    }
    else{
        // on the falling edge, stop the timer and store the duration
        echoTime = TimerValueGet(TIMER1_BASE, TIMER_A);
        TimerDisable(TIMER1_BASE, TIMER_A);
        // Distance = echoTime/(clock frequency*58 us)
        distance = echoTime / 2320;

        // Turn on LED and send HIGH or LOW to ESP-01
        if(distance > 40){
            GPIOPinWrite(GPIO_PORTF_BASE, GPIO_PIN_1|GPIO_PIN_2|GPIO_PIN_3,0x02); //Red
            if(currentStatus == true){
                currentStatus = false;
            transmit("UPDATE `parkingspaces` SET `SpaceNum`=2,`IsFilled`=\"empty\" WHERE `SpaceNum`=2;"); //There isn't a car

            }

        }else{
            GPIOPinWrite(GPIO_PORTF_BASE, GPIO_PIN_1|GPIO_PIN_2|GPIO_PIN_3,0x08);//Green
            if(currentStatus == false){
               currentStatus = true;
           transmit("UPDATE `parkingspaces` SET `SpaceNum`=2,`IsFilled`=\"taken\" WHERE `SpaceNum`=2;"); //There is a car
        }
        }

        // Tell the trigger to pulse again
        triggerOn = true;
    }

}

//function to write string to UART
void SendATCommand(char *cmd)
{
    while(UARTBusy(UART4_BASE));
    while(*cmd != '\0')
    {
        UARTCharPut(UART4_BASE, *cmd++);
    }
    UARTCharPut(UART4_BASE, '\r'); //commands must end in \r\n to signify the end of the command
    UARTCharPut(UART4_BASE, '\n');
}

void transmit(char * str) //function to send a sequence of AT command to transmit data to the server
{
    SendATCommand("AT+CIPSTART=\"TCP\",\"99.251.21.107\",27015"); //connect to server at a certain IP address and port
    SysCtlDelay((SysCtlClockGet() * 1) / 3);
    SendATCommand("AT+CIPSEND=80"); //prepare the esp to send a string
    SysCtlDelay((SysCtlClockGet() * 1) / 3);
    SendATCommand(str); // send the string
    SysCtlDelay((SysCtlClockGet() * 1) / 3);
    SendATCommand("AT+CIPCLOSE");


}

