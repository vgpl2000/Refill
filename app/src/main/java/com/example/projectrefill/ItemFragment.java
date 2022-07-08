package com.example.projectrefill;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;


public class ItemFragment extends Fragment {

    Button btn_add_items;
    RecyclerView recyclerViewforitemdisplay;
    adapter_clientside_itemdisplaying adapter;
    SearchView searchView;
    TextView nosearch;
    ProgressBar progressBar;



    //check network connected or not function
    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_item, container, false);

        //clear backstack
        FragmentManager fm=getFragmentManager();
        fm.popBackStack("add_item",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("edit_item",FragmentManager.POP_BACK_STACK_INCLUSIVE);

        //check internet

        if(!isNetworkConnected()){
            Intent intent = new Intent(getActivity().getApplication(), no_internet_client.class);
            startActivity(intent);

        }

        nosearch=v.findViewById(R.id.textviewfornosearchresult);
        progressBar=v.findViewById(R.id.progressbar1);

        recyclerViewforitemdisplay=v.findViewById(R.id.recyclerView_items);
        recyclerViewforitemdisplay.setLayoutManager(new CustomLinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<client_model_todisplayitemsavailable> options =
                new FirebaseRecyclerOptions.Builder<client_model_todisplayitemsavailable>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_items"), client_model_todisplayitemsavailable.class)
                        .build();
        adapter=new adapter_clientside_itemdisplaying(options);

        recyclerViewforitemdisplay.setAdapter(adapter);

        recyclerViewforitemdisplay.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                progressBar.setVisibility(View.GONE);
                nosearch.setVisibility(View.INVISIBLE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        searchView=v.findViewById(R.id.searchView_items);

        btn_add_items=v.findViewById(R.id.btn_add_items);
        
        btn_add_items.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getActivity(), "Add new Items here...", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        btn_add_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.wrapper4,new client_btnplus_add_item_Fragment()).addToBackStack("add_item");
                fr.commit();


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


        return  v;
    }

    private void mySearch(String s) {

        FirebaseRecyclerOptions<client_model_todisplayitemsavailable> options =
                new FirebaseRecyclerOptions.Builder<client_model_todisplayitemsavailable>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_items").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), client_model_todisplayitemsavailable.class)
                        .build();

        adapter=new adapter_clientside_itemdisplaying(options);
        if(adapter.getItemCount()!=0){
            nosearch.setVisibility(View.INVISIBLE);

        }else if (adapter.getItemCount()==0){

            nosearch.setText("No such item named as "+s);
            nosearch.setVisibility(View.VISIBLE);
        }
        adapter.startListening();
        recyclerViewforitemdisplay.setAdapter(adapter);

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


    //this is for avoiding inconsistancy error please check
    public class CustomLinearLayoutManager extends LinearLayoutManager {
        public CustomLinearLayoutManager(Context context) {
            super(context);
        }

        //Generate constructors

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }

    
}