package com.example.projectrefill;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Checkordersbtn_client_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String name;

    public Checkordersbtn_client_Fragment() {

    }
    public Checkordersbtn_client_Fragment(String name) {
        this.name=name;
    }
    public static Checkordersbtn_client_Fragment newInstance(String param1, String param2) {
        Checkordersbtn_client_Fragment fragment = new Checkordersbtn_client_Fragment();
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
        View view=inflater.inflate(R.layout.fragment_checkordersbtn_client_, container, false);

        TextView textView=view.findViewById(R.id.rname);
        textView.setText(name);


        return view;
    }


   public void onBackPressed()
    {
        AppCompatActivity appCompatActivity=(AppCompatActivity)getContext();
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper2,new HomeFragment()).addToBackStack(null).commit();

    }
}