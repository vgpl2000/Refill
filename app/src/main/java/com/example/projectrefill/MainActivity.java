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
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://project-refill-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login=findViewById(R.id.btn_login);
        EditText txtUser=findViewById(R.id.txtUser);
        EditText txtPassword=findViewById(R.id.txtPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String eUser=txtUser.getText().toString();
                final String ePassword=txtPassword.getText().toString();

                if(eUser.isEmpty()||ePassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter the credentials", Toast.LENGTH_LONG).show();
                }
                else{
                    databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check data exist in firebase
                            if(snapshot.hasChild(eUser)){
                                //match data
                                final String getPassword=snapshot.child("eUSer").child("password").getValue(String.class);
                                if(getPassword.equals("password")){
                                    Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

    }




}