package com.example.projectrefill;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class client_btnplus_add_item_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Declaration
    Button btn_add;
    ImageView img;
    TextInputEditText i_name;
    TextInputEditText i_price;
    TextInputEditText i_weight;
    TextInputEditText i_quan;




    public client_btnplus_add_item_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static client_btnplus_add_item_Fragment newInstance(String param1, String param2) {
        client_btnplus_add_item_Fragment fragment = new client_btnplus_add_item_Fragment();
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
        View v=inflater.inflate(R.layout.fragment_client_btnplus_add_item_, container, false);


        btn_add=v.findViewById(R.id.btn_add_item_client);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                i_name=v.findViewById(R.id.item_name);
                i_price=v.findViewById(R.id.item_price);
                i_quan=v.findViewById(R.id.item_quan);
                i_weight=v.findViewById(R.id.item_weight);



                String str_iname=i_name.getText().toString();
                String str_iprice=i_price.getText().toString();
                String str_iquan=i_quan.getText().toString();
                String str_iweight=i_weight.getText().toString();


                dataholder_add_item obj=new dataholder_add_item(str_iprice,str_iquan,str_iweight);
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference node=db.getReference("Client").child("c_items");

                node.child(str_iname).setValue(obj);

                i_name.setText("");
                i_price.setText("");
                i_quan.setText("");
                i_weight.setText("");

            }
        });







        return v;
    }
}