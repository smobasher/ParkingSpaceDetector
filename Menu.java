package com.client.myapplication.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        }
    public void openPAge(View view) {
        Intent intent = new Intent(Menu.this, MainActivity.class);
        startActivity(intent);
    }

}