package com.example.projectrefill;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class retailerside_datewisetransaction_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String date;
    TextView dateval;


    public retailerside_datewisetransaction_Fragment() {
        // Required empty public constructor
    }
    public retailerside_datewisetransaction_Fragment(String date) {
        this.date=date;
    }


    public static retailerside_datewisetransaction_Fragment newInstance(String param1, String param2) {
        retailerside_datewisetransaction_Fragment fragment = new retailerside_datewisetransaction_Fragment();
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

        View view=inflater.inflate(R.layout.fragment_retailerside_datewisetransaction_, container, false);
        dateval=view.findViewById(R.id.texttodispdateforref);
        dateval.setText(date);


        return view;
    }
}