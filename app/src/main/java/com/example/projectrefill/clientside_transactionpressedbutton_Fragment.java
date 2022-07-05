package com.example.projectrefill;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class clientside_transactionpressedbutton_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name;
    RecyclerView recyclerView4;
    adapter_clientside_transactionbuttonpressed adapter;



    public clientside_transactionpressedbutton_Fragment() {
        // Required empty public constructor
    }


    public static clientside_transactionpressedbutton_Fragment newInstance(String param1, String param2) {
        clientside_transactionpressedbutton_Fragment fragment = new clientside_transactionpressedbutton_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public clientside_transactionpressedbutton_Fragment(String name) {
        this.name =name;
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
        View view=inflater.inflate(R.layout.fragment_clientside_transactionpressedbutton_, container, false);
        TextView textView=view.findViewById(R.id.textViewtodispnamefortrans);
        textView.setText(name);

        String nameforretailer=textView.getText().toString();

        //TextView namewhereitexists=view.findViewById(R.id.itshouldnotbevisibleatanycost);

        //namewhereitexists.setText("Amritha Stores");

        recyclerView4=(RecyclerView) view.findViewById(R.id.recyclerViewfortransactionsdisplay);

        LinearLayoutManager linearLayoutManager=new CustomLinearLayoutManager1(getContext());
        //linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView4.setLayoutManager(linearLayoutManager);

        //recyclerView4.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<client_model_todispalldetailswhendatepressed> options2 =
                new FirebaseRecyclerOptions.Builder<client_model_todispalldetailswhendatepressed>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer").child(nameforretailer).child("r_history"), client_model_todispalldetailswhendatepressed.class)
                        .build();
        adapter=new adapter_clientside_transactionbuttonpressed(options2);
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

    public void onBackPressed()
    {
        AppCompatActivity appCompatActivity=(AppCompatActivity)getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper2,new HomeFragment()).addToBackStack(null).commit();

    }
}