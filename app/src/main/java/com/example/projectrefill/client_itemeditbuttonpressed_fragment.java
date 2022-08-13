package com.example.projectrefill;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class client_itemeditbuttonpressed_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name;
    Button submit;
    Button delete;
    EditText price,weight;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference1 = database.getInstance().getReference();

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

        submit=view.findViewById(R.id.btntoupdateitemfromdatabase);
        delete=view.findViewById(R.id.btntodeleteitemfromdatabase);
        String name11=textView.getText().toString();
        price=view.findViewById(R.id.newprice);
        weight=view.findViewById(R.id.newweight);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String newprice = price.getText().toString();
                String newweight = weight.getText().toString();

                if (newprice.isEmpty() || newweight.isEmpty()) {
                    price.setError("Price must be given");
                    weight.setError("Weight must be given");


                } else {


                    databaseReference1.child("Client").child("c_items").child(name11).child("price").setValue(newprice);
                    databaseReference1.child("Client").child("c_items").child(name11).child("weight").setValue(newweight);

                    price.setText("");
                    weight.setText("");

                    InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.edititemclosefrag, new ItemFragment()).addToBackStack(null);
                    fr.commit();


                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete!!");

                builder.setMessage("Are you sure you want to delete "+name11+" ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference remove=FirebaseDatabase.getInstance().getReference("Client").child("c_items").child(name11);
                        remove.removeValue();

                        FragmentTransaction fr= getFragmentManager().beginTransaction();
                        fr.replace(R.id.edititemclosefrag,new ItemFragment());
                        fr.commit();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });




        return view;
    }
}