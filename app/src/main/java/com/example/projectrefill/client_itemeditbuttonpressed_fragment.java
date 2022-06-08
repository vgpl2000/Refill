package com.example.projectrefill;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class client_itemeditbuttonpressed_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name;

    public client_itemeditbuttonpressed_fragment() {
        // Required empty public constructor
    }

    public client_itemeditbuttonpressed_fragment(String name) {
        this.name =name;
    }

    public static client_itemeditbuttonpressed_fragment newInstance(String param1, String param2) {
        client_itemeditbuttonpressed_fragment fragment = new client_itemeditbuttonpressed_fragment();
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

        View view=inflater.inflate(R.layout.fragment_client_itemeditbuttonpressed_fragment, container, false);

        TextView textView=view.findViewById(R.id.nametodisplayinedit);
        textView.setText(name);
        ImageView buttonclose=view.findViewById(R.id.btntoclosefragmentedit);
        buttonclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.edititemclosefrag,new ItemFragment());
                fr.commit();
            }
        });




        return view;
    }
}