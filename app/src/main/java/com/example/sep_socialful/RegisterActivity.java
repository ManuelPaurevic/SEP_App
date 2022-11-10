package com.example.sep_socialful;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;


    private EditText userNicknameET;
    private EditText userPhoneNumberET;
    private EditText userEmailET;
    private EditText userAgeET;
    private EditText userPasswordET;
    private EditText userCPasswordET;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNicknameET = (EditText) findViewById(R.id.nickNameInput);
        userPhoneNumberET = (EditText) findViewById(R.id.phoneNumberInput);
        userEmailET = (EditText) findViewById(R.id.emailInput);
        userAgeET = (EditText) findViewById(R.id.ageInput);
        userPasswordET = (EditText) findViewById(R.id.passwordRInput);
        userCPasswordET = (EditText) findViewById(R.id.confirmPasswordRInput);

        mAuth = FirebaseAuth.getInstance();

        //Sets up the Spinner
        Spinner genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        Button registerButton = (Button) findViewById(R.id.buttonReg);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });

        Button backButton = (Button) findViewById(R.id.backRegButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void createNewAccount(){
        //Grabs values from edit texts
        String nickName = userNicknameET.getText().toString();
        String phoneNumber = userPhoneNumberET.getText().toString();
        String email = userEmailET.getText().toString();
        String age = userAgeET.getText().toString();
        String password = userPasswordET.getText().toString();
        String confirmPassword = userCPasswordET.getText().toString();

        //boolean validCreds = validateCreds(nickName, phoneNumber, email, ageS, password, confirmPassword);

        if (TextUtils.isEmpty(email)){
            userEmailET.setError("Email cannot be empty");
            userEmailET.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            userPasswordET.setError("Password cannot be empty");
            userPasswordET.requestFocus();
        }else if (!password.equals(confirmPassword)){
            userCPasswordET.setError("Passwords must match");
            userCPasswordET.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Account Created successfully", Toast.LENGTH_SHORT).show();

                        SendInfo(email, password, nickName, phoneNumber, age);
                        FirebaseAuth.getInstance().signOut();

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void SendInfo(String email, String password, String nickName, String phoneNumber, String age){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String uid = user.getUid( );

                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference("Users");

                            UserAccount newUser = new UserAccount(nickName, email, gender, phoneNumber, age);
                            myRef.child(uid).setValue(newUser);


                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        gender = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }





}