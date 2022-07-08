package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
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


public class settings_client_delivere3_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String date,name;
    RecyclerView recyclerView;
    TextView headtext;
    adapter_client_setting_can3 adapter;

    public settings_client_delivere3_Fragment() {
        // Required empty public constructor
    }

    public settings_client_delivere3_Fragment(String date, String name) {
        this.date = date;
        this.name = name;
    }

    public static settings_client_delivere3_Fragment newInstance(String param1, String param2) {
        settings_client_delivere3_Fragment fragment = new settings_client_delivere3_Fragment();
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
        View v= inflater.inflate(R.layout.fragment_settings_client_delivere3_, container, false);

        ProgressBar progressBar;
        progressBar=v.findViewById(R.id.progressbar1);

        recyclerView=v.findViewById(R.id.recyclerViewtodispdateinaccp2);

        LinearLayoutManager linearLayoutManager=new CustomLinearLayoutManager1(getContext());
        //linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        SharedPreferences preferences;
        preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username=preferences.getString("username","");


        FirebaseRecyclerOptions<client_model_setting_accp3> options =
                new FirebaseRecyclerOptions.Builder<client_model_setting_accp3>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_delivered").child(name).child("date").child(date).child("Items"),client_model_setting_accp3.class)
                        .build();

        adapter=new adapter_client_setting_can3(options);


        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                progressBar.setVisibility(View.GONE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        headtext=v.findViewById(R.id.headtext);
        headtext.setText("Details of delivered orders on "+date);


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