package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String user = "arunim";
    String pw = "1234567";

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
        }
        else
        {
            Toast loginNo = Toast.makeText(this, "Incorrect Password!",
                    Toast.LENGTH_SHORT);
            loginNo.show();
        }
    }
}
