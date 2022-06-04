package com.example.projectrefill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class change_pswrd_Fragment extends Fragment {

    EditText txt_c_psswd;
    EditText txt_n_passwd;
    Button btn_chng_passwd;
    String c_passwrd,n_passwrd;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();

    public change_pswrd_Fragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_change_pswrd_, container, false);

        txt_c_psswd=v.findViewById(R.id.txt_c_passwrd);
        txt_n_passwd=v.findViewById(R.id.txt_n_passwrd);
        btn_chng_passwd=v.findViewById(R.id.btn_chng_passwrd);




        btn_chng_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c_passwrd = txt_c_psswd.getText().toString();
                n_passwrd = txt_n_passwd.getText().toString();


                if (c_passwrd.isEmpty() && n_passwrd.isEmpty()) {
                    txt_c_psswd.setError("Enter credentials!");
                    txt_n_passwd.setError("Enter credentials!");
                } else {


                    databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String d_c_passwd = snapshot.child("akashadeepa").child("password").getValue(String.class);

                            if (c_passwrd.equals(d_c_passwd)) {
                                databaseReference.child("Client").child("akashadeepa").child("password").setValue(n_passwrd);

                                Toast.makeText(getActivity(), "Password Updated!", Toast.LENGTH_SHORT).show();
                                
                                txt_c_psswd.setText("");
                                txt_n_passwd.setText("");

                                AppCompatActivity appCompatActivity=(AppCompatActivity)getContext();
                                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.chng_passwd,new SettingsFragment()).addToBackStack(null).commit();

                            } else {
                                txt_c_psswd.setError("Password is wrong!");
                                txt_n_passwd.setError("Password is wrong!");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

        });


        return v;

    }


}