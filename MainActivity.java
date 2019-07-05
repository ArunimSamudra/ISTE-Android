package com.example.mymusicplayer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity
{

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00BFFF")));
    }

    final String user = "arunim";
    final String pw = "1234567";

    public void check(View view)
    {
        TextView userText =
                (TextView) findViewById(R.id.editText3);

        String userString = userText.getText().toString();


        TextView pwText =
                (TextView) findViewById(R.id.editText);

        String pwString = pwText.getText().toString();

        if(user.compareTo(userString)==0 && pw.compareTo(pwString)==0)
        {
            Toast loginYes = Toast.makeText(this, "Login Successful!",
                    Toast.LENGTH_SHORT);
            loginYes.show();
            Intent i = new Intent(this,MusicList.class);
            startActivity(i);
        }
        else
        {
            Toast loginNo = Toast.makeText(this, "Login Insuccessful!",
                    Toast.LENGTH_SHORT);
            loginNo.show();
        }
    }
}
