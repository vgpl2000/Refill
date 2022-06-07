package com.example.projectrefill;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView1;
    adapter_clientside_order_list adapter;
    SearchView searchView;


    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    //for git
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        //recycler added
        //just for github purpose
        //just for github purpose2
        //just for github purpose3
        //just for github purpose4
        //just for github purpose5

        //To search
        searchView=v.findViewById(R.id.searchView);

        recyclerView1=(RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<client_model_home_orders> options =
                new FirebaseRecyclerOptions.Builder<client_model_home_orders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_orders"), client_model_home_orders.class)
                        .build();
        adapter=new adapter_clientside_order_list(options);
        recyclerView1.setAdapter(adapter);



        SearchManager searchManager= (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                mySearch(s);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mySearch(s);
                return true;
            }

        });



        /*v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });*/


        return v;
    }

    private void mySearch(String s) {

        FirebaseRecyclerOptions<client_model_home_orders> options =
                new FirebaseRecyclerOptions.Builder<client_model_home_orders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_orders").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), client_model_home_orders.class)
                        .build();

        adapter=new adapter_clientside_order_list(options);
        adapter.startListening();
        recyclerView1.setAdapter(adapter);

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
}