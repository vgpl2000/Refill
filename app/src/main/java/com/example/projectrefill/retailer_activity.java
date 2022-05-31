package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.projectrefill.databinding.ActivityClientBinding;
import com.example.projectrefill.databinding.ActivityRetailerBinding;

public class retailer_activity extends AppCompatActivity {
ActivityRetailerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRetailerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replacefragment(new HomeFragment());
                    break;
                case R.id.cart:
                    replacefragment(new ItemFragment());
                    break;
                case R.id.info:
                    replacefragment(new RetailerFragment());
                    break;
                case R.id.settings:
                    replacefragment(new SettingsFragment());
                    break;
            }


            return true;
        });
    }
    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framel,fragment);
        fragmentTransaction.commit();
    }
}