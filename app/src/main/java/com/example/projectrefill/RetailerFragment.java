package com.example.projectrefill;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RetailerFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    SearchView searchView;
    adapter_clientside_retailerdetailesdisplying adapter;
    TextView noresult;
    ProgressBar progressBar;


    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;

        }
    }

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

        //clear backstack
        FragmentManager fm=getFragmentManager();
        fm.popBackStack("r_trans",FragmentManager.POP_BACK_STACK_INCLUSIVE);

        //check internet
        if(!isNetworkConnected()){
            Intent intent = new Intent(getActivity().getApplication(), no_internet_client.class);
            startActivity(intent);

        }


        searchView=v.findViewById(R.id.searchViewforretailerlist);
        noresult=v.findViewById(R.id.textviewfornosearchresult3);
        progressBar=v.findViewById(R.id.progressbar1);

        recyclerView=v.findViewById(R.id.recyclerviewtodispretailerlist);
        recyclerView.setLayoutManager(new RetailerFragment.CustomLinearLayoutManager1(getContext()));

        FirebaseRecyclerOptions<client_model_fordisplayingretailerstoupdatedue> options =
                new FirebaseRecyclerOptions.Builder<client_model_fordisplayingretailerstoupdatedue>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer"),client_model_fordisplayingretailerstoupdatedue.class)
                        .build();
        adapter=new adapter_clientside_retailerdetailesdisplying(options);
        //when count is 0
        if(adapter.getItemCount()!=0){
            noresult.setVisibility(View.VISIBLE);

        }
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                progressBar.setVisibility(View.GONE);
                noresult.setVisibility(View.INVISIBLE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        SearchManager searchManager= (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                try{
                    String output = s.substring(0, 1).toUpperCase() + s.substring(1);
                    searchView.clearFocus();
                    mySearch(output);
                }catch (Exception e){
                    e.printStackTrace();
                    mySearch(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try{
                    noresult.setVisibility(View.VISIBLE);
                    String output = s.substring(0, 1).toUpperCase() + s.substring(1);
                    mySearch(output);
                }catch (Exception e){
                    e.printStackTrace();
                    mySearch(s);
                }
                return true;
            }

        });



        return v;
    }

    private void mySearch(String s) {

        FirebaseRecyclerOptions<client_model_fordisplayingretailerstoupdatedue> options =
                new FirebaseRecyclerOptions.Builder<client_model_fordisplayingretailerstoupdatedue>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), client_model_fordisplayingretailerstoupdatedue.class)
                        .build();

        adapter=new adapter_clientside_retailerdetailesdisplying(options);

        if (adapter.getItemCount()==0){
            noresult.setText("No such retailer named as "+s);
            noresult.setVisibility(View.VISIBLE);
        }

        adapter.startListening();
        recyclerView.setAdapter(adapter);
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