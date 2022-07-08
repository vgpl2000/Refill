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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Home_Retailer_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();
    adapter_retailerside_homepage_itemdisplay adapter;
    SearchView searchView;
    ProgressBar progressBar;

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


        //check internet
        if(!isNetworkConnected()){
            Intent intent = new Intent(getActivity().getApplication(), no_internet_retailer.class);
            startActivity(intent);

        }


        noresult=v.findViewById(R.id.textviewfornosearchresultrt1);
        noresult.setVisibility(View.INVISIBLE);
        progressBar=v.findViewById(R.id.progressbar1);
        searchView=v.findViewById(R.id.searchViewrthome);


        //blocked or not
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor=preferences.edit();
        String name1=preferences.getString("username","");
        databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String state=snapshot.child(name1).child("state").getValue(String.class);

                if(state.equals("blocked")){
                    editor.putString("state","blocked");
                    editor.commit();
                    Toast toast=Toast.makeText(getActivity(),name1+ "can't use Refill right now!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    editor.putString("notblocked","");
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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