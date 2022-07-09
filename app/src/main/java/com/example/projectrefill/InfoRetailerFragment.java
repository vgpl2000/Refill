package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InfoRetailerFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageView btn_close;
    TextView abttxt;
    RatingBar ratingbar;


    private String mParam1;
    private String mParam2;

    public InfoRetailerFragment() {
        // Required empty public constructor
    }


    public static InfoRetailerFragment newInstance(String param1, String param2) {
        InfoRetailerFragment fragment = new InfoRetailerFragment();
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
        View v= inflater.inflate(R.layout.fragment_info_retailer, container, false);

        btn_close=v.findViewById(R.id.btn_close_chng_r);
        abttxt=v.findViewById(R.id.abttxt);
        ratingbar=v.findViewById(R.id.ratingbar);



        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.aboutinfo_r, new Settings_Retailer_Fragment());
                fr.commit();
            }
        });

        //get ratings and set

        DatabaseReference setrate=FirebaseDatabase.getInstance().getReference();
        SharedPreferences preferences;
        preferences=getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String r_name=preferences.getString("username","");


        setrate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("Ratings")){
                    //if has ratings, then set it
                    setrate.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Float rate=snapshot.child("Ratings").child(r_name).getValue(Float.class);
                            ratingbar.setRating(rate);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    ratingbar.setRating(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //when rating changes
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                SharedPreferences preferences;
                preferences=getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String r_name=preferences.getString("username","");
                DatabaseReference ratingref= FirebaseDatabase.getInstance().getReference("Ratings");
                ratingref.child(r_name).setValue(v);
            }
        });

        return v;
    }

    private void closeKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}