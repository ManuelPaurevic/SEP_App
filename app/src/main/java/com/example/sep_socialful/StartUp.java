package com.example.sep_socialful;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        Runnable onRun = new Runnable() {
            @Override
            public void run() {
                openLogin();
                //openMain();
            }
        };
        Handler h = new Handler();
        h.postDelayed(onRun, 1600);

    }
    //JASON CHANGES
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}