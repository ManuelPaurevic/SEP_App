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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    FirebaseUser user = mAuth.getCurrentUser();

    List<String> userCommunities = new ArrayList<>();
    private TextView nickNameTV;
    private TextView ageTV;
    private TextView emailTV;
    private TextView commTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InputUserData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nickNameTV = (TextView) requireView().findViewById(R.id.nicknameProfile);
        ageTV = (TextView) requireView().findViewById(R.id.ageProfile);
        emailTV = (TextView) requireView().findViewById(R.id.emailProfile);
        commTV = (TextView) requireView().findViewById(R.id.communityJoinedText);

        Button signOutButton = (Button) requireView().findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });
    }

    private void InputUserData(){
        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,String> userDataMap = (Map<String,String>) snapshot.getValue();

                String nickName = userDataMap.get("nickName");
                String age = userDataMap.get("age");
                String email = userDataMap.get("email");

                nickNameTV.setText( " " + nickName);
                ageTV.setText(age);
                emailTV.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("firedata" , "dataRecived " + "Failed");
            }
        });

        DatabaseReference comRef = database.getReference("Users_In_Community").child(user.getUid());

        //Checks if data is already there
        comRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //Takes data from firebase and adds it to a list
                    comRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                comRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        UsersCommunity communities = snapshot.getValue(UsersCommunity.class);
                                        userCommunities = Arrays.asList(communities.getCommunities().split(","));

                                        String comText = "";
                                        for (int i =0; i < userCommunities.size(); i++){
                                            if(i == 0){
                                                comText = "Communitys: " + userCommunities.get(i);
                                            } else {
                                                comText = comText + ", " + userCommunities.get(i);
                                            }
                                        }
                                        commTV.setText(comText);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }
                    });

                } else {
                   commTV.setText("Not in a Community Yet!");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }

        });
    }

    private void SignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


}