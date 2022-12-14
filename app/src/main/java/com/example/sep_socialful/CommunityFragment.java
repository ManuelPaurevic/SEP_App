package com.example.sep_socialful;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommunityFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference communityRef = database.getReference("community_details");
    DatabaseReference userRef;
    FirebaseUser user = mAuth.getCurrentUser();

    RecyclerView communityRecycler;
    communityAdapter communityAdapter;
    ArrayList<community> communities;
    List<String> userCommunities = new ArrayList<>();
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetUserCommunity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();

        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Runnable onRun = new Runnable() {
            @Override
            public void run() {
                addToRecycler();
            }
        };
        Handler h = new Handler();
        h.postDelayed(onRun, 300);
    }

    public void removeCommunity(int position){
        communities.remove(position);
        communityAdapter.notifyItemRemoved(position);
    }

    public void addCommunityUser(int position){
        String uid = user.getUid();
        userRef = database.getReference("Users_In_Community").child(uid);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                UsersCommunity gottenValue = task.getResult().getValue(UsersCommunity.class);
                                String com = communities.get(position).getName();
                                String col = com + "," + gottenValue.getCommunities();
                                UsersCommunity uic = new UsersCommunity(col);
                                userRef.setValue(uic);
                                removeCommunity(position);
                            }
                        }
                    });
                } else {
                    String com = communities.get(position).getName();
                    UsersCommunity uic = new UsersCommunity(com);
                    userRef.setValue(uic);
                    removeCommunity(position);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }

        });
    }

    public void addToRecycler(){
        communityRecycler = (RecyclerView) requireView().findViewById(R.id.communityRecycler);
        communityRecycler.setHasFixedSize(true);
        communityRecycler.setLayoutManager(new LinearLayoutManager(context));

        communities = new ArrayList<>();
        communityAdapter = new communityAdapter(context, communities);
        communityRecycler.setAdapter(communityAdapter);

        communityAdapter.setOnItemClickListener(new communityAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                addCommunityUser(position);
            }
        });

        communityRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean flag = true;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    community community = dataSnapshot.getValue(community.class);
                    for(int i = 0; i < userCommunities.size(); i++){
                        if(userCommunities.get(i).equals(community.getName()) && flag){
                            flag = false;
                        }
                    }
                    if(flag){
                        communities.add(community);
                    }
                    flag = true;

                }
                communityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

   public void GetUserCommunity() {
       String uid = user.getUid();
       userRef = database.getReference("Users_In_Community").child(uid);

       //Checks if data is already there
       userRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot snapshot) {
               if (snapshot.exists()) {

                   //Takes data from firebase and adds it to a list
                   userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DataSnapshot> task) {
                           if (task.isSuccessful()) {
                               userRef.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       UsersCommunity communities = snapshot.getValue(UsersCommunity.class);
                                       userCommunities = Arrays.asList(communities.getCommunities().split(","));
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {
                                   }
                               });
                           }
                       }
                   });

               } else {
                   userCommunities.add("N/A");
               }
           }

           @Override
           public void onCancelled(DatabaseError error) {
           }

       });

   }
}