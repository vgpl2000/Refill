package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class HomeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView1;
    adapter_clientside_order_list adapter;
    SearchView searchView;
    TextView noorders;

    TextView noresult;

    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;

        }
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);

        //clear backstack
        FragmentManager fm=getFragmentManager();
        fm.popBackStack("home_orders",FragmentManager.POP_BACK_STACK_INCLUSIVE);

        //check internet
        if(!isNetworkConnected()){
            Intent intent = new Intent(getActivity().getApplication(), no_internet_client.class);
            startActivity(intent);

        }



        //recycler added
        noresult=v.findViewById(R.id.textviewfornosearchresult2);
        //To search
        searchView=v.findViewById(R.id.searchView);
        noorders=v.findViewById(R.id.textviewfornoorders);


        recyclerView1=(RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView1.setLayoutManager(new CustomLinearLayoutManager1(getContext()));


        FirebaseRecyclerOptions<client_model_home_orders> options =
                new FirebaseRecyclerOptions.Builder<client_model_home_orders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_orders"), client_model_home_orders.class)
                        .build();

        adapter=new adapter_clientside_order_list(options);
        adapter.startListening();
        recyclerView1.setAdapter(adapter);

        //on scroll recycler
        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                noorders.setVisibility(View.GONE);

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

        FirebaseRecyclerOptions<client_model_home_orders> options =
                new FirebaseRecyclerOptions.Builder<client_model_home_orders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_orders").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), client_model_home_orders.class)
                        .build();

        adapter=new adapter_clientside_order_list(options);
        if (adapter.getItemCount()==0){
            noresult.setText("No such retailer named as "+s);
            noresult.setVisibility(View.VISIBLE);
        }
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

    //solution for inconsistency detected error
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