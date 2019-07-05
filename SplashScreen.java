package com.example.mymusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        },500);

    }
}
