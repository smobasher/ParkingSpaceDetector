package com.client.myapplication.client;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.Boolean.TRUE;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    Thread Thread1 = null;

    Button btnSend,btnSend2, btnSend3,btnSend4, btnSend5, btnSend6, btnSend7, btnSend8, btnSend9, btnSend10,printToConsole, refresh;
   // TextView tvmessages;
    String SERVER_IP;
    int SERVER_PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = findViewById(R.id.refresh);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend2 = findViewById(R.id.btnSend2);
        btnSend2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend3 = findViewById(R.id.btnSend3);
        btnSend3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend4 = findViewById(R.id.btnSend4);
        btnSend5 = findViewById(R.id.btnSend5);
        btnSend5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend6 = findViewById(R.id.btnSend6);
        btnSend6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend7 = findViewById(R.id.btnSend7);
        btnSend7.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend8 = findViewById(R.id.btnSend8);
        btnSend8.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend9 = findViewById(R.id.btnSend9);
        btnSend9.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btnSend10 = findViewById(R.id.btnSend10);
        btnSend10.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        printToConsole=findViewById(R.id.printToConsole);
        final RelativeLayout relativeLayout;

        SERVER_IP = "99.251.21.107";
        SERVER_PORT = 27015;
        Thread1 = new Thread(new Thread1());
        Thread1.start();


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
     //   android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();

     //   bg.setColor(new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}},new int[] {Color.parseColor("#EB6864"), Color.parseColor("#00A98D")}));
    //    bg.setShape(1);
    //    bg.setCornerRadius(10);
      //  bg.setStroke(2, new android.content.res.ColorStateList(new int[][] {{-android.R.attr.state_pressed}, {android.R.attr.state_pressed}}, new int[] {Color.parseColor("#505050"), Color.parseColor("#00A98D")}));


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "UPDATE `parkingspaces` SET `SpaceNum`=1,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=1;";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    btnSend.setText("Spot 1 Reserved");
                    btnSend.setBackgroundColor(getResources().getColor(R.color.orangeHolo));

                }
            }
        });

        btnSend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message2 = "UPDATE `parkingspaces` SET `SpaceNum`=2,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=2;";
                if (!message2.isEmpty()) {
                    new Thread(new Thread3(message2)).start();
                    btnSend2.setText("Spot 2 Reserved");
                    btnSend2.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });

        btnSend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message3 = "UPDATE `parkingspaces` SET `SpaceNum`=3,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=3;";
                if (!message3.isEmpty()) {
                    new Thread(new Thread3(message3)).start();
                    btnSend3.setText("Spot 3 Reserved");
                    btnSend3.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });

        btnSend4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message3 = "UPDATE `parkingspaces` SET `SpaceNum`=4,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=4;";
                if (!message3.isEmpty()) {
                    new Thread(new Thread3(message3)).start();
                    btnSend4.setText("Spot 4 Reserved");
                    btnSend4.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });
        btnSend5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "UPDATE `parkingspaces` SET `SpaceNum`=5,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=5;";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    btnSend5.setText("Spot 5 Reserved");
                    btnSend5.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });

        btnSend6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "UPDATE `parkingspaces` SET `SpaceNum`=6,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=6;";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    btnSend6.setText("Spot 6 Reserved");
                    btnSend6.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });

        btnSend7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "UPDATE `parkingspaces` SET `SpaceNum`=7,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=7;";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    btnSend7.setText("Spot 7 Reserved");
                    btnSend7.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });

        btnSend8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "UPDATE `parkingspaces` SET `SpaceNum`=8,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=8;";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    btnSend8.setText("Spot 8 Reserved");
                    btnSend8.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });
        btnSend9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "UPDATE `parkingspaces` SET `SpaceNum`=9,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=9;";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    btnSend9.setText("Spot 9 Reserved");
                    btnSend9.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });

        btnSend10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "UPDATE `parkingspaces` SET `SpaceNum`=10,`IsFilled`=\"rsrvd\" WHERE `SpaceNum`=10;";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                    btnSend10.setText("Spot 10 Reserved");
                    btnSend10.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                }
            }
        });
        printToConsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "select * from parkingspaces;";
                if (!message.isEmpty()) {
                    new Thread(new Thread3(message)).start();
                }
            }
        });

    }

    private PrintWriter output;
    private BufferedReader input;

    class Thread1 implements Runnable {

        @Override
        public void run() {
            Socket socket;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);

                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });
                new Thread(new Thread2()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String message = input.readLine();
                    /*
                    tried to split strngs to seperate strings so i wouldnt have to manually test but it kept crashing the app
                    String [] strs = message.split (" ");

                    String one = strs[0];
                    String two = strs[1];

                    * */

                    if (message != null) {

                        if(message.equals("2empty 3rsrvd") == TRUE)
                        {
                            btnSend2.setText("Spot 2 empty");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            btnSend3.setText("Spot 3 reserved");
                            btnSend3.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                        }
                        /* Sample of how I would have used split string:
                        else if((strs[0].equals("2rsrvd") == TRUE)||(strs[1].equals("2rsrvd") == TRUE))
                        {
                            btnSend2.setText("Spot 2 Reserved");
                        }
                        */
                        else if(message.equals("2rsrvd 3rsrvd") == TRUE)
                        {
                            btnSend2.setText("Spot 2 reserved");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                            btnSend3.setText("Spot 3 reserved");
                            btnSend3.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                        }
                        else if(message.equals("2rsrvd 3empty") == TRUE)
                        {
                            btnSend2.setText("Spot 2 reserved");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                            btnSend3.setText("Spot 3 empty");
                            btnSend.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else if(message.equals("2taken 3rsrvd") == TRUE)
                        {
                            btnSend2.setText("Spot 2 taken");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.redd));
                            btnSend3.setText("Spot 3 reserved");
                            btnSend3.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                        }
                        else if(message.equals("2rsrvd 3taken") == TRUE)
                        {
                            btnSend2.setText("Spot 2 reserved");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.orangeHolo));
                            btnSend3.setText("Spot 3 taken");
                            btnSend3.setBackgroundColor(getResources().getColor(R.color.redd));
                        }
                        else if(message.equals("2taken 3taken") == TRUE)
                        {
                            btnSend2.setText("Spot 2 taken");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.redd));
                            btnSend3.setText("Spot 3 taken");
                            btnSend3.setBackgroundColor(getResources().getColor(R.color.redd));
                        }
                        else if(message.equals("2empty 3taken") == TRUE)
                        {
                            btnSend2.setText("Spot 2 empty");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            btnSend3.setText("Spot 3 taken");
                            btnSend3.setBackgroundColor(getResources().getColor(R.color.redd));
                        }
                        else if(message.equals("2taken 3empty") == TRUE)
                        {
                            btnSend2.setText("Spot 2 taken");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.redd));
                            btnSend3.setText("Spot 3 empty");
                            btnSend3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else if(message.equals("2empty 3empty") == TRUE)
                        {
                            btnSend2.setText("Spot 2 empty");
                            btnSend2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            btnSend3.setText("Spot 3 empty");
                            btnSend3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                        //        tvmessages.append("server: " + message + "\n");
                            }
                        });
                    } else {
                        Thread1 = new Thread(new Thread1());
                        Thread1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Thread3 implements Runnable {
        private String message;

        Thread3(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            output.write(message);
            output.flush();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }
}


