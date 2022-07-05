package com.example.projectrefill;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class clientside_datebutton_pressed_detaildisp_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView4;
    String date;



    public clientside_datebutton_pressed_detaildisp_Fragment() {
        // Required empty public constructor
    }

    public clientside_datebutton_pressed_detaildisp_Fragment(String date) {
        this.date = date;
    }

    public static clientside_datebutton_pressed_detaildisp_Fragment newInstance(String param1, String param2) {
        clientside_datebutton_pressed_detaildisp_Fragment fragment = new clientside_datebutton_pressed_detaildisp_Fragment();
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
       View view= inflater.inflate(R.layout.fragment_clientside_datebutton_pressed_detaildisp_, container, false);

        recyclerView4=(RecyclerView) view.findViewById(R.id.recyclerViewdatewisedipofdetailsfrc);
        recyclerView4.setLayoutManager(new CustomLinearLayoutManager1(getContext()));

        TextView nameofuser=view.findViewById(R.id.itshouldnotbevisibleatanycost);
        String name=nameofuser.getText().toString();



        return view;
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