package com.example.projectrefill;

import android.app.SearchManager;
import android.content.Context;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class Home_Retailer_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    adapter_retailerside_homepage_itemdisplay adapter;
    SearchView searchView;
    ProgressBar progressBar;
    TextView noresult;




    public Home_Retailer_Fragment() {
        //Required empty public constructor
    }


    public static Home_Retailer_Fragment newInstance(String param1, String param2) {
        Home_Retailer_Fragment fragment = new Home_Retailer_Fragment();
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
        View v= inflater.inflate(R.layout.fragment_home__retailer_, container, false);


        progressBar=v.findViewById(R.id.progressBarHomerthome);

        noresult=v.findViewById(R.id.textviewfornosearchresultrt1);
        noresult.setVisibility(View.INVISIBLE);

        searchView=v.findViewById(R.id.searchViewrthome);

        recyclerView=(RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager1(getContext()));



        FirebaseRecyclerOptions<retailer_model_home_recycler_itemdisplaying> options =
                new FirebaseRecyclerOptions.Builder<retailer_model_home_recycler_itemdisplaying>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_items"), retailer_model_home_recycler_itemdisplaying.class)
                        .build();


        adapter=new adapter_retailerside_homepage_itemdisplay(options);

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
                    progressBar.setVisibility(View.VISIBLE);
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

        FirebaseRecyclerOptions<retailer_model_home_recycler_itemdisplaying> options =
                new FirebaseRecyclerOptions.Builder<retailer_model_home_recycler_itemdisplaying>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_items").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), retailer_model_home_recycler_itemdisplaying.class)
                        .build();

        adapter=new adapter_retailerside_homepage_itemdisplay(options);

         if (adapter.getSnapshots().isEmpty()){
            progressBar.setVisibility(View.GONE);
            noresult.setText("No such item named as "+s);
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