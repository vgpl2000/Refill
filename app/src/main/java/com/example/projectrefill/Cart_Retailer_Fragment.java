package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class Cart_Retailer_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SharedPreferences preferences;
    adapter_retailerside_cart_display adapter;
    Button placeorder;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    retailer_model_placeorder_pressed model=null;
    Integer ftot=0;

    DatabaseReference databaseReference = database.getInstance().getReference();


    public Cart_Retailer_Fragment() {
        // Required empty public constructor
    }


    public static Cart_Retailer_Fragment newInstance(String param1, String param2) {
        Cart_Retailer_Fragment fragment = new Cart_Retailer_Fragment();
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

        View v= inflater.inflate(R.layout.fragment_cart__retailer_, container, false);


        //blocked or not
        SharedPreferences.Editor editor;
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


        preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username=preferences.getString("username","");


        progressBar=v.findViewById(R.id.progressBarcart);

        recyclerView=(RecyclerView) v.findViewById(R.id.recyclerViewtoshowcart);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager1(getContext()));

        FirebaseRecyclerOptions<retailer_model_cart_retailer> options =
                new FirebaseRecyclerOptions.Builder<retailer_model_cart_retailer>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer").child(username).child("r_orders"), retailer_model_cart_retailer.class)
                        .build();

        adapter=new adapter_retailerside_cart_display(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                progressBar.setVisibility(View.GONE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        placeorder=v.findViewById(R.id.buttontoplaceorder);
        RadioGroup radioGroup;
        RadioButton cash,credit;
        radioGroup=v.findViewById(R.id.rgroup);



        databaseReference.child("Retailer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final String getdueamt = snapshot.child(username).child("r_orders").child("Benne Murku").child("totalamount").getValue(String.class);

                Integer tot=Integer.parseInt(getdueamt);
                ftot=ftot+tot;
                System.out.println(ftot+" some what values");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(radioGroup.getCheckedRadioButtonId()==-1){
                Toast.makeText(getContext(), "select atleast one", Toast.LENGTH_SHORT).show();
            }else {
                RadioButton select=v.findViewById(radioGroup.getCheckedRadioButtonId());
                String pmode = select== null ? "" : select.getText().toString();

                String iname,price,quan,weight;

                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String username=preferences.getString("username","");


                    TextView itemname = v.findViewById(R.id.cart_name);

                    System.out.println(itemname.getText().toString() + " dlsjfljslkfjlsfjls");


                    final HashMap<String, Object> cart = new HashMap<>();

                   // cart.put("name", model.getName());
                   // cart.put("price", model.getPrice());
                    //cart.put("date", formattedDate);
                   // cart.put("quan", model.quan);
                   // cart.put("weight", model.getWeight());


                /*DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Retailer");
                databaseReference.child(username).child("r_orders").child(iname).updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {


                        if (task.isSuccessful()){

                            holder.update.setVisibility(View.GONE);

                        }else {
                            Toast.makeText(view.getContext(), "If its big error we will make updates", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

                databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                if(pmode.equals("Cash")){

                }else if(pmode.equals("Credit")){

                }
            }
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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