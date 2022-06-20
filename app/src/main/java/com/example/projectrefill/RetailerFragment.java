package com.example.projectrefill;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RetailerFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    adapter_clientside_retailerdetailesdisplying adapter;


    public RetailerFragment() {
        // Required empty public constructor
    }


    public static RetailerFragment newInstance(String param1, String param2) {
        RetailerFragment fragment = new RetailerFragment();
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

        View v= inflater.inflate(R.layout.fragment_retailer, container, false);

        recyclerView=v.findViewById(R.id.recyclerviewtodispretailerlist);
        recyclerView.setLayoutManager(new RetailerFragment.CustomLinearLayoutManager1(getContext()));

        FirebaseRecyclerOptions<client_model_fordisplayingretailerstoupdatedue> options =
                new FirebaseRecyclerOptions.Builder<client_model_fordisplayingretailerstoupdatedue>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer"),client_model_fordisplayingretailerstoupdatedue.class)
                        .build();
        adapter=new adapter_clientside_retailerdetailesdisplying(options);
        recyclerView.setAdapter(adapter);



        return v;
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