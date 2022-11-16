package com.example.sep_socialful;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GoogleMapFragment extends Fragment {

    private Geocoder geocoder;
    float zoomLevel = 12.0f;
    DatabaseReference reference;
    GoogleMap map;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                googleMap.setMyLocationEnabled(true);
            } else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }

            /*
            AsyncTask<String, Void, LatLng> task = new getCoordsFromAddress().execute("8309 MacKenzie Rd");
            LatLng london = null;

            try {
                london = task.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            googleMap.addMarker(new MarkerOptions().position(london).title("Marker in london"));


             */


            readMarkData(map);
        }

    };

    private class getCoordsFromAddress extends AsyncTask<String,Void,LatLng>{
        protected LatLng doInBackground(String... strings) {
            String address = strings[0];
            LatLng coords = null;
            Address place;
            geocoder = new Geocoder(getActivity());
            try{
                List<Address> list = geocoder.getFromLocationName(address, 1);
                place = list.get(0);
                coords = new LatLng(place.getLatitude(), place.getLongitude());
            }catch (IOException e){
                e.printStackTrace();
            }
            return coords;
        }
    }



    private void readMarkData(GoogleMap mMap){
        reference = FirebaseDatabase.getInstance().getReference().child("Communities").child("AllEvents");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Event> temp = new ArrayList<>();
                for(DataSnapshot ss : snapshot.getChildren()){
                    temp.add(ss.getValue(Event.class));
                }
                for(int i = 0; i < temp.size(); i++){
                    //LatLng cords = getCoordsFromAddress(temp.get(i).getEvent_Address());
                    AsyncTask<String, Void, LatLng> cords  = new getCoordsFromAddress().execute(temp.get(i).getEvent_Address());

                    try {
                        mMap.addMarker(new MarkerOptions().position(cords.get()).title(temp.get(i).getEvent_Name()).snippet("Date: " + temp.get(0).getEvent_Date() + ", Time: " + temp.get(i).getEvent_Time()));
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    public LatLng getCoordsFromAddress(String address){
        LatLng coords = null;
        Address place;
        geocoder = new Geocoder(getActivity());
        try{
            List<Address> list = geocoder.getFromLocationName(address, 1);
            place = list.get(0);
            coords = new LatLng(place.getLatitude(), place.getLongitude());
        }catch (IOException e){
            e.printStackTrace();
        }
        return coords;
    }

     */


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}
