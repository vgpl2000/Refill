package com.example.projectrefill;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectrefill.databinding.ActivityClientBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;


public class client_activity extends AppCompatActivity {
ActivityClientBinding binding;
Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            binding = ActivityClientBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            if(!isConnected()){
                Intent intent = new Intent(client_activity.this, no_internet_client.class);
                startActivity(intent);
            }


        //logged in sets in database
        DatabaseReference logstatus=FirebaseDatabase.getInstance().getReference("Client").child("akashadeepa");
            logstatus.child("logstatus").setValue("loggedin");


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        FirebaseDatabase.getInstance().getReference("Client").child("akashadeepa").child("token").setValue(token);
                        // Log and toast

                    }
                });

            replacefragment(new HomeFragment());



                binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.home:
                            replacefragment(new HomeFragment());
                            //check internet
                            ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
                            if((connectivityManager.getActiveNetworkInfo()!=null)&&(connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting())==false){
                                Intent intent = new Intent(client_activity.this, no_internet_client.class);
                                startActivity(intent);
                            }
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
        private void replacefragment (Fragment fragment){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.framel, fragment);
            fragmentTransaction.commit();
        }

        @Override
        public void onBackPressed () {
            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }



     /*Button logout=findViewById(R.id.logout);

    public void setLogout(Button logout) {
        this.logout = logout;
    }*/


    public boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null&&connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}

