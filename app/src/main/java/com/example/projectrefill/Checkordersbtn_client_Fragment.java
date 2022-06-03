package com.example.projectrefill;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Checkordersbtn_client_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();
    String name;
    RecyclerView recyclerView2;
    adapter_clientside_checkbtn adapter2;

    public Checkordersbtn_client_Fragment() {

    }
    public Checkordersbtn_client_Fragment(String name) {
        this.name=name;
    }
    public static Checkordersbtn_client_Fragment newInstance(String param1, String param2) {
        Checkordersbtn_client_Fragment fragment = new Checkordersbtn_client_Fragment();
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
        View view=inflater.inflate(R.layout.fragment_checkordersbtn_client_, container, false);

        TextView textView=view.findViewById(R.id.rname);
        textView.setText(name);
        recyclerView2=(RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        String rtname=textView.getText().toString();
        System.out.println("Value of rtname: "+rtname);



        String pname=databaseReference.child("Client").child("c_orders").child(rtname).getParent().getKey();

        FirebaseRecyclerOptions<client_model_btncheckorders> options2 =
                new FirebaseRecyclerOptions.Builder<client_model_btncheckorders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_orders").child("amritha_stores").child("check_orders"), client_model_btncheckorders.class)
                        .build();
        adapter2=new adapter_clientside_checkbtn(options2);
        recyclerView2.setAdapter(adapter2);


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter2.stopListening();
    }


   public void onBackPressed()
    {
        AppCompatActivity appCompatActivity=(AppCompatActivity)getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper2,new HomeFragment()).addToBackStack(null).commit();

    }
}