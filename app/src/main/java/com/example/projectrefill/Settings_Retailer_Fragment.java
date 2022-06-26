package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings_Retailer_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings_Retailer_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button logout;
    ImageView retailer_profile;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView retailer_name;
    TextView txtchngpassword_r;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();






    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Settings_Retailer_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings_Retailer_Fragment.
     */
    // TODO: Rename and change types and number of parameters
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

        //Disable back button for this fragment
        /*v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });*/

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
                Toast.makeText(getActivity(), "Profile Image goes here...", Toast.LENGTH_SHORT).show();
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

                Intent intent=new Intent(getActivity(),splash_screen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        txtchngpassword_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.settings_retailer,new change_pswrd_retailer_Fragment()).addToBackStack(null);
                fr.commit();

            }
        });

        return v;
    }
}