package com.example.projectrefill;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class client_btnplus_add_item_Fragment extends Fragment {




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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_client_btnplus_add_item_, container, false);

        img=v.findViewById(R.id.imageViewfritem);
        btn_add=v.findViewById(R.id.btn_add_item_client);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getActivity(), "Image comes here", Toast.LENGTH_SHORT).show();
            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                i_name=v.findViewById(R.id.item_name);
                i_price=v.findViewById(R.id.item_price);
                i_quan=v.findViewById(R.id.item_quan);
                i_weight=v.findViewById(R.id.item_weight);



                String str_iname=i_name.getText().toString();

                //String str_iprice=i_price.getText().toString();

                Integer str_iprice=Integer.parseInt(i_price.getText().toString());

                //String str_iquan=i_quan.getText().toString();

                Integer str_iquan =Integer.parseInt(i_quan.getText().toString());

                //String str_iweight=i_weight.getText().toString();

                Integer str_iweight =Integer.parseInt(i_weight.getText().toString());


                dataholder_add_item obj=new dataholder_add_item(str_iprice,str_iquan,str_iweight);
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference node=db.getReference("Client").child("c_items");

                node.child(str_iname).setValue(obj);

                Toast.makeText(getActivity(), "Added data", Toast.LENGTH_SHORT).show();

                i_name.setText("");
                i_price.setText("");
                i_quan.setText("");
                i_weight.setText("");

            }
        });







        return v;
    }
}