package com.example.sep_socialful;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button loginButton;
    private TextView registerGoTo;
    private EditText userEmailET;
    private EditText userPasswordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        userEmailET = (EditText) findViewById(R.id.emailLogin);
        userPasswordET = (EditText) findViewById(R.id.passwordLogin);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerGoTo = (TextView) findViewById(R.id.textViewRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                String userEmail = userEmailET.getText().toString();
                String userPassword = userPasswordET.getText().toString();

                if (TextUtils.isEmpty(userEmail)){
                    userEmailET.setError("Email cannot be empty");
                    userEmailET.requestFocus();
                }else if (TextUtils.isEmpty(userPassword)){
                    userPasswordET.setError("Password cannot be empty");
                    userPasswordET.requestFocus();
                } else{
                    LoginUser(userEmail, userPassword);
                }
            }
        });


        registerGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) { openReg(); }
        });
    }

    private void LoginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            openMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {

    }

    //Sends to MainActivity
    public void openMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //Send to RegisterActivity
    public void openReg(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}