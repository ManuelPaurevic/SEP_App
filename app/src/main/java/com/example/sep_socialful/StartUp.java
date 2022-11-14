package com.example.sep_socialful;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartUp extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        Runnable onRun = new Runnable() {
            @Override
            public void run() {
                CheckUserStatus();
            }
        };
        Handler h = new Handler();
        h.postDelayed(onRun, 1500);

    }

    private void CheckUserStatus(){
        if(currentUser != null){
            openMain();
        }else{
            openLogin();
        }
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