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
        replacefragment(new Home_Retailer_Fragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replacefragment(new Home_Retailer_Fragment());
                    break;
                case R.id.cart:
                    replacefragment(new Cart_Retailer_Fragment());
                    break;
                case R.id.info:
                    replacefragment(new Info_Retailer_Fragment());
                    break;
                case R.id.settings:
                    replacefragment(new Settings_Retailer_Fragment());
                    break;
            }


            return true;
        });
    }
    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framel2,fragment);
        fragmentTransaction.commit();
    }
}