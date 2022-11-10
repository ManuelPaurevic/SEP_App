package com.example.sep_socialful;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;


public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    FirebaseUser user = mAuth.getCurrentUser();

    private TextView nickNameTV;
    private TextView ageTV;
    private TextView emailTV;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InputUserData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nickNameTV = (TextView) requireView().findViewById(R.id.nicknameProfile);
        ageTV = (TextView) requireView().findViewById(R.id.ageProfile);
        emailTV = (TextView) requireView().findViewById(R.id.emailProfile);

        Button signOutButton = (Button) requireView().findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();

            }
        });



    }

    private void InputUserData(){

        Log.v("firedata" , "dataRecived " + user.getUid());

        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,String> userDataMap = (Map<String,String>) snapshot.getValue();

                String nickName = userDataMap.get("nickName");
                String age = userDataMap.get("age");
                String email = userDataMap.get("email");

                nickNameTV.setText(nickName);
                ageTV.setText(age);
                emailTV.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("firedata" , "dataRecived " + "Failed");
            }
        });
    }

    private void SignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


}