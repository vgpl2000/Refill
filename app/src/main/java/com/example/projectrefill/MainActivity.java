package com.example.projectrefill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btn_login;
    EditText username,u_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        username = findViewById(R.id.txtUser);
        u_password = findViewById(R.id.txtPassword);

    }
        public void onClick (View view){
            String fusername = username.getText().toString().trim();
            String fpassword = u_password.getText().toString().trim();

            Toast.makeText(this, "Onclick running", Toast.LENGTH_SHORT).show();

            Query chkUser = FirebaseDatabase.getInstance().getReference("Client").orderByChild("username").equalTo(fusername);
            chkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        username.setError(null);
                        Toast.makeText(MainActivity.this, "Client exist", Toast.LENGTH_SHORT).show();

                        String t_password = dataSnapshot.child(fpassword).getValue(String.class);
                        if (t_password.equals(fpassword)) {
                            u_password.setError(null);
                            Toast.makeText(MainActivity.this, "Client password wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
            Query chkRetailer = FirebaseDatabase.getInstance().getReference("Retailer").orderByChild("username").equalTo(fusername);
            chkRetailer.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        username.setError(null);
                        Toast.makeText(MainActivity.this, "User exists", Toast.LENGTH_SHORT).show();

                        String t_password = dataSnapshot.child(fusername).child("password").getValue(String.class);
                        if (t_password.equals(fpassword)) {
                            u_password.setError(null);
                            Toast.makeText(MainActivity.this, "Password is right", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "User do not exists", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }



}