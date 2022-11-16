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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GoogleMapFragment extends Fragment {

    private Geocoder geocoder;
    float zoomLevel = 12.0f; //This goes up to 21
    GoogleMap map;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            //LatLng london = getCoordsFromAddress("5314 staely ave");

            AsyncTask<String, Void, LatLng> task = new getCoordsFromAddress().execute("8309 MacKenzie Rd");
            LatLng london = null;

            try {
                london = task.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            googleMap.addMarker(new MarkerOptions().position(london).title("Marker in london"));


            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                googleMap.setMyLocationEnabled(true);
            } else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }

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
