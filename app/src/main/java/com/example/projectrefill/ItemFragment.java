package com.example.projectrefill;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class ItemFragment extends Fragment {

    Button btn_add_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_item, container, false);


        btn_add_items=v.findViewById(R.id.btn_add_items);

        btn_add_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.wrapper4,new client_btnplus_add_item_Fragment()).addToBackStack(null);
                fr.commit();


            }
        });

        return  v;
    }

    
}