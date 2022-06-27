package com.example.projectrefill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projectrefill.databinding.ActivityClientBinding;
import com.example.projectrefill.databinding.ActivityRetailerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class retailer_activity extends AppCompatActivity {
ActivityRetailerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //blocked or not
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getInstance().getReference();
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        preferences = this.getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor=preferences.edit();
        String name1=preferences.getString("username","");
        databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String state=snapshot.child(name1).child("state").getValue(String.class);

                if(state.equals("blocked")){
                    editor.putString("state","blocked");
                    editor.commit();
                    Toast.makeText(retailer_activity.this, "You don't have access!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(retailer_activity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    editor.putString("notblocked","");
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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