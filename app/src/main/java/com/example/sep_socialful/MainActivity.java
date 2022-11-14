package com.example.sep_socialful;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.sep_socialful.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new GoogleMapFragment());
        binding.bottomNavigationView.getMenu().getItem(1).setChecked(true);

        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.map:
                    replaceFragment(new GoogleMapFragment());
                    break;
                case R.id.community:
                    replaceFragment(new CommunityFragment());
                    break;
            }
            return true;
        });

    }

    @Override
    public void onBackPressed() {

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }


}