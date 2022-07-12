package com.example.projectrefill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class OrderPlacedFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    MediaPlayer mediaPlayer;


    private String mParam1;
    private String mParam2;

    public OrderPlacedFragment() {
        // Required empty public constructor
    }

    public static OrderPlacedFragment newInstance(String param1, String param2) {
        OrderPlacedFragment fragment = new OrderPlacedFragment();
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
        View v= inflater.inflate(R.layout.fragment_order_placed, container, false);

        Thread thread=new Thread(){
            public void run(){
                //sound starts

                mediaPlayer= MediaPlayer.create(getActivity(),R.raw.order_placed_sound);
                mediaPlayer.start();

                try{
                    sleep(3000);


                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    //fragment transaction after 1000ms
                    AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.order_placed, new Cart_Retailer_Fragment()).addToBackStack("placed").commit();
                    mediaPlayer.stop();

                }

            }

        };thread.start();



        return  v;
    }
}