package com.example.projectrefill;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class settings_client_delivere1_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    adapter_client_setting_del1 adapter;
    RecyclerView recyclerView;

    public settings_client_delivere1_Fragment() {
        // Required empty public constructor
    }


    public static settings_client_delivere1_Fragment newInstance(String param1, String param2) {
        settings_client_delivere1_Fragment fragment = new settings_client_delivere1_Fragment();
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
        View v= inflater.inflate(R.layout.fragment_settings_client_delivere1_, container, false);

        ProgressBar progressBar;
        progressBar=v.findViewById(R.id.progressbar1);

        recyclerView=v.findViewById(R.id.recyclerViewtodispdateinaccp1);

        LinearLayoutManager linearLayoutManager=new CustomLinearLayoutManager1(getContext());
        //linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        FirebaseRecyclerOptions<client_model_setting_accp1> options =
                new FirebaseRecyclerOptions.Builder<client_model_setting_accp1>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_delivered"),client_model_setting_accp1.class)
                        .build();

        adapter=new adapter_client_setting_del1(options);


        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                TextView noresult;
                noresult=v.findViewById(R.id.textviewfornosearchresult);
                noresult.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });


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