package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

import com.example.projectrefill.databinding.ActivityClientBinding;

public class client_activity extends AppCompatActivity {
ActivityClientBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replacefragment(new HomeFragment());
                    break;
                case R.id.item:
                    replacefragment(new ItemFragment());
                    break;
                case R.id.retailer:
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