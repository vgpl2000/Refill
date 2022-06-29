package com.example.projectrefill;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class change_pswrd_Fragment extends SettingsFragment {

    EditText txt_c_psswd;
    EditText txt_n_passwd;
    Button btn_chng_passwd;
    ImageView btn_close;
    String c_passwrd,n_passwrd;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_change_pswrd_, container, false);

        txt_c_psswd=v.findViewById(R.id.txt_c_passwrd);
        txt_n_passwd=v.findViewById(R.id.txt_n_passwrd);
        btn_chng_passwd=v.findViewById(R.id.btn_chng_passwrd);
        btn_close=v.findViewById(R.id.btn_close_chng);

        //Close button
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeKeyboard();

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.chng_passwd,new SettingsFragment());
                fr.commit();
                
            }
        });



        btn_chng_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                c_passwrd = txt_c_psswd.getText().toString();
                n_passwrd = txt_n_passwd.getText().toString();


                if (c_passwrd.isEmpty() && n_passwrd.isEmpty()) {
                    txt_c_psswd.setError("Enter credentials!");
                    txt_n_passwd.setError("Enter credentials!");
                } else {

                    closeKeyboard();

                    //checking
                    if (c_passwrd.equals(n_passwrd)) {
                        txt_n_passwd.setError("New Password cannot be same as Current Password");
                    }else{
                    databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String d_c_passwd = snapshot.child("akashadeepa").child("password").getValue(String.class);

                            if (c_passwrd.equals(d_c_passwd)) {
                                databaseReference.child("Client").child("akashadeepa").child("password").setValue(n_passwrd);

                                Toast.makeText(getActivity(), "Password Updated!", Toast.LENGTH_SHORT).show();

                                txt_c_psswd.setText("");
                                txt_n_passwd.setText("");


                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                fr.replace(R.id.chng_passwd, new SettingsFragment());
                                fr.commit();

                            } else {
                                txt_c_psswd.setError("Password is wrong!");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                }
            }

        });


        return v;

    }


    private void closeKeyboard() {
        View view=this.getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager keyboard=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}