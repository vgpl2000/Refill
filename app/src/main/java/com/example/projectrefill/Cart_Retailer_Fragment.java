package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class Cart_Retailer_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SharedPreferences preferences;
    adapter_retailerside_cart_display adapter;


    public Cart_Retailer_Fragment() {
        // Required empty public constructor
    }


    public static Cart_Retailer_Fragment newInstance(String param1, String param2) {
        Cart_Retailer_Fragment fragment = new Cart_Retailer_Fragment();
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

        View v= inflater.inflate(R.layout.fragment_cart__retailer_, container, false);

        preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username=preferences.getString("username","");


        progressBar=v.findViewById(R.id.progressBarcart);

        recyclerView=(RecyclerView) v.findViewById(R.id.recyclerViewtoshowcart);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager1(getContext()));

        FirebaseRecyclerOptions<retailer_model_cart_retailer> options =
                new FirebaseRecyclerOptions.Builder<retailer_model_cart_retailer>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer").child(username).child("r_orders"), retailer_model_cart_retailer.class)
                        .build();

        adapter=new adapter_retailerside_cart_display(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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