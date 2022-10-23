package com.example.sep_socialful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView registerGoTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerGoTo = (TextView) findViewById(R.id.textViewRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) { openMain(); }
        });
        registerGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) { openMain(); }
        });
    }

    //Sends to MainActivity
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //Send to RegisterActivity
    public void openReg(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}