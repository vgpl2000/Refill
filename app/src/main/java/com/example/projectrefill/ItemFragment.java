package com.example.projectrefill;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

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
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;


public class ItemFragment extends Fragment {

    Button btn_add_items;
    RecyclerView recyclerViewforitemdisplay;
    adapter_clientside_itemdisplaying adapter;
    SearchView searchView;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_item, container, false);

        recyclerViewforitemdisplay=v.findViewById(R.id.recyclerView_items);
        recyclerViewforitemdisplay.setLayoutManager(new CustomLinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<client_model_todisplayitemsavailable> options =
                new FirebaseRecyclerOptions.Builder<client_model_todisplayitemsavailable>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_items"), client_model_todisplayitemsavailable.class)
                        .build();
        adapter=new adapter_clientside_itemdisplaying(options);
        recyclerViewforitemdisplay.setAdapter(adapter);


        searchView=v.findViewById(R.id.searchView_items);

        btn_add_items=v.findViewById(R.id.btn_add_items);

        btn_add_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.wrapper4,new client_btnplus_add_item_Fragment()).addToBackStack(null);
                fr.commit();


            }
        });






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


        return  v;
    }

    private void mySearch(String s) {

        FirebaseRecyclerOptions<client_model_todisplayitemsavailable> options =
                new FirebaseRecyclerOptions.Builder<client_model_todisplayitemsavailable>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_items").orderByChild("name").startAt(s.toUpperCase()).endAt(s.toLowerCase()+"\uf8ff"), client_model_todisplayitemsavailable.class)
                        .build();

        adapter=new adapter_clientside_itemdisplaying(options);
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