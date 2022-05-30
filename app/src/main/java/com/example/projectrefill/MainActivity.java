package com.example.projectrefill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    TextView forgot;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    DatabaseReference databaseReference = database.getInstance().getReference();


    //https://project-refill-default-rtdb.asia-southeast1.firebasedatabase.app/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button login = findViewById(R.id.btn_login);
        EditText txtUser = findViewById(R.id.txtUser);
        EditText txtPassword = findViewById(R.id.txtPassword);
        TextView forgot = findViewById(R.id.textView6);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Forget", Toast.LENGTH_SHORT).show();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ePassword = txtPassword.getText().toString();
                final String eUser = txtUser.getText().toString();


                if (eUser.isEmpty() || ePassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter the credentials", Toast.LENGTH_LONG).show();
                } else {
                    databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check data exist in firebase
                            if (snapshot.hasChild(eUser)) {
                                //match data
                                final String getPassword = snapshot.child(eUser).child("password").getValue(String.class);

                                if (ePassword.equals(getPassword)) {
                                    Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }


        });
    }
}








