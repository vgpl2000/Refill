package com.example.projectrefill;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class clientside_datebutton_pressed_detaildisp_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView4;
    String date;
    String nameforretailer;
    adapter_clientside_ultimate_displaying_retailerinfo adapter;



    public clientside_datebutton_pressed_detaildisp_Fragment() {
        // Required empty public constructor
    }

    public clientside_datebutton_pressed_detaildisp_Fragment(String date,String nameforretailer) {
        this.date = date;
        this.nameforretailer=nameforretailer;
    }


    public static clientside_datebutton_pressed_detaildisp_Fragment newInstance(String param1, String param2) {
        clientside_datebutton_pressed_detaildisp_Fragment fragment = new clientside_datebutton_pressed_detaildisp_Fragment();
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
       View view= inflater.inflate(R.layout.fragment_clientside_datebutton_pressed_detaildisp_, container, false);

        recyclerView4=(RecyclerView) view.findViewById(R.id.recyclerViewdatewisedipofdetailsfrc);
        recyclerView4.setLayoutManager(new CustomLinearLayoutManager1(getContext()));

        //TextView nameofuser=view.findViewById(R.id.itshouldnotbevisibleatanycost);
        //String name=nameofuser.getText().toString();

        FirebaseRecyclerOptions<client_model_ultimatedispofretinfo> options2 =
                new FirebaseRecyclerOptions.Builder<client_model_ultimatedispofretinfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer").child(nameforretailer).child("r_history").child(date).child("Items"), client_model_ultimatedispofretinfo.class)
                        .build();
        adapter=new adapter_clientside_ultimate_displaying_retailerinfo(options2);
        recyclerView4.setAdapter(adapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    public class CustomLinearLayoutManager1 extends LinearLayoutManager {
        public CustomLinearLayoutManager1(Context context) {
            super(context);
        }
        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }
}