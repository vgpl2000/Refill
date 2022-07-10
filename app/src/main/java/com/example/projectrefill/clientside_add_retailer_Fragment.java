package com.example.projectrefill;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

public class clientside_add_retailer_Fragment extends Fragment {

    EditText txt_name;
    EditText txt_passwd;
    Button btn_add_r;
    ImageView btn_close;
    String r_name,r_passwrd;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public clientside_add_retailer_Fragment() {
        // Required empty public constructor
    }


    public static clientside_add_retailer_Fragment newInstance(String param1, String param2) {
        clientside_add_retailer_Fragment fragment = new clientside_add_retailer_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_clientside_add_retailer_, container, false);

        btn_close=v.findViewById(R.id.r_btn_close_chng);
        txt_name=v.findViewById(R.id.r_txt_n_name);
        txt_passwd=v.findViewById(R.id.r_txt_n_passwrd);
        btn_add_r=v.findViewById(R.id.r_btn_add_submit);


        r_name=txt_name.getText().toString();
        r_passwrd=txt_passwd.getText().toString();


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

        //submit new retailer
        btn_add_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r_name=txt_name.getText().toString();
                r_passwrd=txt_passwd.getText().toString();
                if(r_name.isEmpty()&&r_passwrd.isEmpty()){
                    txt_name.setError("Name cannot be empty");
                    txt_name.requestFocus();
                }else if(r_passwrd.isEmpty()){
                    txt_passwd.setError("Password cannot be empty");
                    txt_passwd.requestFocus();

                }else{
                    //if no field is empty...


                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("Retailer").hasChild(r_name)){
                                txt_name.setError("Retailer already exists");
                            }else{
                                //if there is no retailer as such then create retailer...

                                closeKeyboard();

                                DatabaseReference createref=FirebaseDatabase.getInstance().getReference("Retailer");
                                createref.child(r_name).child("due_amt").setValue("0");
                                createref.child(r_name).child("name").setValue(r_name);
                                createref.child(r_name).child("password").setValue(r_passwrd);
                                createref.child(r_name).child("state").setValue("notblocked");
                                createref.child(r_name).child("logstatus").setValue("loggedout");

                                Toast.makeText(getActivity(), r_name+" added!", Toast.LENGTH_SHORT).show();


                                FragmentTransaction fr= getFragmentManager().beginTransaction();
                                fr.replace(R.id.chng_passwd,new SettingsFragment());
                                fr.commit();

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

    private void closeKeyboard() {
        View view=this.getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager keyboard=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}