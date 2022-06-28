package com.example.projectrefill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    CheckBox showPassword;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    DatabaseReference databaseReference = database.getInstance().getReference();

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    //https://project-refill-default-rtdb.asia-southeast1.firebasedatabase.app/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = preferences.edit();
        Button login = findViewById(R.id.btn_login);
        EditText txtUser = findViewById(R.id.txtUser);
        EditText txtPassword = findViewById(R.id.txtPassword);
        progressBar = findViewById(R.id.progressBar);
        showPassword = findViewById(R.id.checkBox);

        //Check user already logged in or not

        if (preferences.contains("username")) {
            //Toast.makeText(this, "Preference checking...", Toast.LENGTH_LONG).show();
            String resText = preferences.getString("username", null);

            if (resText.equals("akashadeepa")) {
                Intent intent = new Intent(MainActivity.this, client_activity.class);
                startActivity(intent);
            } else {
                if (preferences.getString("state", "").equals("blocked")) {

                } else {
                    Intent intent = new Intent(MainActivity.this, retailer_activity.class);
                    startActivity(intent);

                }
            }


            showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            });


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    final String ePassword = txtPassword.getText().toString();
                    final String eUser = txtUser.getText().toString();


                    //Login for Retailer
                    if (eUser.isEmpty() && ePassword.isEmpty()) {
                        txtUser.setError("Username cannot be empty");
                        txtPassword.setError("Password cannot be empty");

                    } else if (eUser.equals("akashadeepa")) {
                        editor.clear();
                        editor.commit();
                        progressBar.setVisibility(View.VISIBLE);
                        databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //check data exist in firebase


                                if (snapshot.hasChild(eUser)) {

                                    //match data
                                    final String getPassword = snapshot.child(eUser).child("password").getValue(String.class);

                                    if (ePassword.equals(getPassword)) {

                                        //save to shared preferences

                                        editor.putString("username", eUser);
                                        editor.putString("password", ePassword);
                                        editor.commit();


                                        Toast toast = Toast.makeText(MainActivity.this, "Welcome Akashadeepa...", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                        Intent intent = new Intent(MainActivity.this, client_activity.class);
                                        startActivity(intent);
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        txtPassword.setError("Credentials do not match");
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } else {
                                    txtUser.setError("Credentials do not match");
                                    progressBar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }
                        });


                    } else {
                        editor.clear();
                        editor.commit();

                        progressBar.setVisibility(View.VISIBLE);

                        databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //check data exist in firebase
                                if (snapshot.hasChild(eUser)) {

                                    //match data
                                    final String getPassword = snapshot.child(eUser).child("password").getValue(String.class);
                                    final String state = snapshot.child(eUser).child("state").getValue(String.class);
                                    if (ePassword.equals(getPassword)) {
                                        Toast toast = Toast.makeText(MainActivity.this, eUser + " Logged In...", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                        //shared preferences
                                        editor.putString("username", eUser);
                                        editor.putString("password", ePassword);
                                        editor.commit();

                                        progressBar.setVisibility(View.GONE);
                                        if (state.equals("notblocked")) {
                                            Intent intent = new Intent(MainActivity.this, retailer_activity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast toast1 = Toast.makeText(MainActivity.this, eUser + " don't have access!", Toast.LENGTH_SHORT);
                                            toast1.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                                            toast1.show();
                                        }

                                    } else {
                                        txtPassword.setError("Credentials do not match");
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } else {
                                    txtUser.setError("Credentials do not match");
                                    progressBar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }


                }


            });

        }

    }
}








