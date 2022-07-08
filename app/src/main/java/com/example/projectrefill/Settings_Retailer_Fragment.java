package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Settings_Retailer_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button logout;
    ImageView retailer_profile;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ImageButton btn_add_r_image;
    TextView retailer_name;
    TextView txtchngpassword_r;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();
    Button accp,can,delivered;
    SwipeRefreshLayout swipeLayout;



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

    public Settings_Retailer_Fragment() {
        // Required empty public constructor
    }


    public static Settings_Retailer_Fragment newInstance(String param1, String param2) {
        Settings_Retailer_Fragment fragment = new Settings_Retailer_Fragment();
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
        View v = inflater.inflate(R.layout.fragment_settings__retailer_, container, false);

        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container1);
        swipeLayout.setOnRefreshListener(this);

        //clear backstack
        FragmentManager fm=getFragmentManager();
        fm.popBackStack("retailer_acc",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("retailer_can",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("retailer_deli",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("r_changepass",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fm.popBackStack("add_profile_ret",FragmentManager.POP_BACK_STACK_INCLUSIVE);


        //check internet
        if(!isNetworkConnected()){
            Intent intent = new Intent(getActivity().getApplication(), no_internet_retailer.class);
            startActivity(intent);

        }

        //blocked or not

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


        //preferences
        preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor=preferences.edit();
        retailer_name = v.findViewById(R.id.txtrname);

        if (preferences.contains("r_name")) {
            String r_name1=preferences.getString("r_name", null);
            retailer_name.setText(r_name1);


        } else {

        //setting retailer name form database
        databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = preferences.getString("username", null);
                if (snapshot.hasChild(name)) {
                    final String r_name = snapshot.child(name).child("name").getValue(String.class);
                    retailer_name.setText(r_name);
                    //adds to shared preferences
                    editor.putString("r_name", r_name);
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error in setting name of retailer", Toast.LENGTH_SHORT).show();

            }
        });
    }




        //To click and change Profile Image

        retailer_profile=v.findViewById(R.id.retailer_profile);

        retailer_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity().getApplication(),settings_retailer_profile_pic_test_activity.class);
                startActivity(intent);
            }
        });
        btn_add_r_image=v.findViewById(R.id.icontoaddprofilepic);

        btn_add_r_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings_retailer,new settings_retailer_profile_pic_Fragment()).addToBackStack("add_profile_ret");
                fr.commit();

                 */
                Intent intent=new Intent(getActivity().getApplication(),settings_retailer_profile_pic_test_activity.class);
                startActivity(intent);
            }
        });

        DatabaseReference pimag=FirebaseDatabase.getInstance().getReference("Retailer").child(retailer_name.getText().toString());

        pimag.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("pimageurl")){
                    String pi=snapshot.child("pimageurl").getValue(String.class);
                    Glide.with(retailer_profile.getContext()).load(pi).into(retailer_profile);
                }else {
                    Glide.with(retailer_profile.getContext()).load(R.drawable.ic_baseline_person_outline_24).into(retailer_profile);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Shared preferences
        preferences=this.getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor=preferences.edit();

        //To logout from retailer

        txtchngpassword_r=v.findViewById(R.id.txtchngpassword_r);

        logout=v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast=Toast.makeText(getActivity(), "Logged out!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();

                editor.clear();
                editor.commit();

                Intent intent=new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        txtchngpassword_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings_retailer,new change_pswrd_retailer_Fragment()).addToBackStack("r_changepass");
                fr.commit();

            }
        });


        accp=v.findViewById(R.id.btn_accepted);
        can=v.findViewById(R.id.btn_cancelled);
        delivered=v.findViewById(R.id.btn_delivered);

        accp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                fm.popBackStack("retailer_acc",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("retailer_can",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("retailer_deli",FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings_retailer,new retailer_setting_accp1_Fragment()).addToBackStack("retailer_acc");
                fr.commit();
            }
        });

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                fm.popBackStack("retailer_acc",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("retailer_can",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("retailer_deli",FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings_retailer,new retailer_setting_can1_Fragment()).addToBackStack("retailer_can");
                fr.commit();
            }
        });

        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                fm.popBackStack("retailer_acc",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("retailer_can",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.popBackStack("retailer_deli",FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings_retailer,new retailer_setting_delivere1_Fragment()).addToBackStack("retailer_deli");
                fr.commit();
            }
        });





        return v;
    }

    @Override
    public void onRefresh() {
        FragmentTransaction fr= getFragmentManager().beginTransaction();
        fr.replace(R.id.settings_retailer,new Settings_Retailer_Fragment());
        fr.commit();

    }
}