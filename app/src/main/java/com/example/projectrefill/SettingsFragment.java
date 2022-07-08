package com.example.projectrefill;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class SettingsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    Button logout,accepted,cancelled,delivered;
    ImageView client_profile;
    TextView txtchngpassword;
    ImageButton btn_add_r;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SwipeRefreshLayout swipeLayout;


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


    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor


    }


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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




    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_settings, container, false);

        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);



        //clear backstack
        FragmentManager fm=getFragmentManager();
        fm.popBackStack("client_acc",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("client_can",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("client_deli",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("add_retailer",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("c_chng_pass",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("add_profile",FragmentManager.POP_BACK_STACK_INCLUSIVE);


        //check internet
       if(!isNetworkConnected()){
           Intent intent = new Intent(getActivity().getApplication(), no_internet_client.class);
           startActivity(intent);

       }


        btn_add_r=v.findViewById(R.id.btn_addretailer);
        logout=v.findViewById(R.id.logout);
        accepted=v.findViewById(R.id.btn_accepted);
        cancelled=v.findViewById(R.id.btn_cancelled);
        delivered=v.findViewById(R.id.btn_delivered);


         txtchngpassword=v.findViewById(R.id.txtchngpassword);
        //To click and change Profile Image write code here
        
        client_profile=v.findViewById(R.id.retailer_profile);

        client_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings1,new setting_profilepic_Fragment()).addToBackStack("add_profile");
                fr.commit();
            }
        });

        ImageButton icontoaddprofile=v.findViewById(R.id.icontoaddprofilepic);

        icontoaddprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings1,new setting_profilepic_Fragment()).addToBackStack("add_profile");
                fr.commit();
            }
        });

        DatabaseReference pimag=FirebaseDatabase.getInstance().getReference("Client").child("akashadeepa");


        pimag.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("pimageurl")){
                    String pi=snapshot.child("pimageurl").getValue(String.class);
                    Glide.with(client_profile.getContext()).load(pi).into(client_profile);
                }else {


                    Glide.with(client_profile.getContext()).load(R.drawable.ic_baseline_person_outline_24).into(client_profile);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //Shared preferences
        preferences=this.getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor=preferences.edit();


        //To add a new retailer
        btn_add_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings1,new clientside_add_retailer_Fragment()).addToBackStack("add_retailer");
                fr.commit();

            }
        });


        //To logout from client

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast=Toast.makeText(getActivity(), "Logged out!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

                
                editor.clear();
                editor.commit();


                Intent intent=new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }


        });



        txtchngpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings1,new change_pswrd_Fragment()).addToBackStack("c_chng_pass");
                fr.commit();

            }
        });


        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                fm.popBackStack("client_acc",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("client_can",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("client_deli",FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings1,new settings_client_accp1_Fragment()).addToBackStack("client_acc");
                fr.commit();

            }
        });

        cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                fm.popBackStack("client_acc",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("client_can",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("client_deli",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings1,new settings_client_can1_Fragment()).addToBackStack("client_can");
                fr.commit();

            }
        });

        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                fm.popBackStack("client_acc",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("client_can",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("client_deli",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings1,new settings_client_delivere1_Fragment()).addToBackStack("client_deli");
                fr.commit();

            }
        });


        return v;
    }

    @Override
    public void onRefresh() {

        FragmentTransaction fr= getFragmentManager().beginTransaction();
        fr.replace(R.id.settings1,new SettingsFragment());
        fr.commit();
    }
}



