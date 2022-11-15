package com.example.sep_socialful;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommunityFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference communityRef = database.getReference("community_details");
    DatabaseReference userRef;
    FirebaseUser user = mAuth.getCurrentUser();

    RecyclerView communityRecycler;
    communityAdapter communityAdapter;
    ArrayList<community> communities;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();

        return inflater.inflate(R.layout.fragment_community, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                removeCommunity(position);
            }
        });

        communityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    community community = dataSnapshot.getValue(community.class);
                    communities.add(community);


                }
                int tracker = 0;
                if(tracker == 0){

                    communityAdapter.notifyDataSetChanged();
                }
                tracker = 1;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void removeCommunity(int position){
        communities.remove(position);
        communityAdapter.notifyItemRemoved(position);
    }

    public void addCommunityUser(int position){
        String uid = user.getUid();
        String com = communities.get(position).getName();
        UsersCommunity uic = new UsersCommunity(com);
        userRef = database.getReference("Users_In_Community").child(uid);
        userRef.setValue(uic);
        Toast.makeText(getActivity(), com, Toast.LENGTH_LONG).show();
    }




}