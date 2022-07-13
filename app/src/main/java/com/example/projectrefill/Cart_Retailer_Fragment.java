package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Cart_Retailer_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //method to check user is offline or not
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
    RecyclerView recyclerView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    adapter_retailerside_cart_display adapter;
    Button placeorder;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    retailer_model_placeorder_pressed model=null;
    TextView nocartitems;
    Integer ftot=0;
    TextView totalamounthere;
    RadioGroup radioGroup;
    String tempmode;

    String token;


    Date c = Calendar.getInstance().getTime();

    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
    String formattedDate = df.format(c);


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

        View v = inflater.inflate(R.layout.fragment_cart__retailer_, container, false);

        //clear backstack
        FragmentManager fm=getFragmentManager();
        fm.popBackStack("placed",FragmentManager.POP_BACK_STACK_INCLUSIVE);


        nocartitems=v.findViewById(R.id.nocartitems);
        //check user is online or not
        if(!isNetworkConnected()){
            Intent intent = new Intent(getActivity().getApplication(), no_internet_retailer.class);
            startActivity(intent);

        }

        //recives total amount from adapter thorugh braodcasting
        totalamounthere = v.findViewById(R.id.totalamountcomeshere);
            LocalBroadcastManager.getInstance(v.getContext())
                    .registerReceiver(msgbrdrec, new IntentFilter("mytotamt"));



        //blocked or not
        SharedPreferences.Editor editor;
        preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = preferences.edit();
        String name1 = preferences.getString("username", "");
        databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String state = snapshot.child(name1).child("state").getValue(String.class);

                if (state.equals("blocked")) {
                    editor.putString("state", "blocked");
                    editor.commit();
                    Toast toast = Toast.makeText(getActivity(), name1 + " can't use Refill right now!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    editor.putString("notblocked", "");
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        //setting recyclerview layout and adapter to display in cart
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewtoshowcart);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager1(getContext()));

        FirebaseRecyclerOptions<retailer_model_cart_retailer> options =
                new FirebaseRecyclerOptions.Builder<retailer_model_cart_retailer>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer").child(username).child("r_orders"), retailer_model_cart_retailer.class)
                        .build();

        adapter = new adapter_retailerside_cart_display(options);
        adapter.startListening();

        recyclerView.setAdapter(adapter);


        //place order btn
        placeorder = v.findViewById(R.id.buttontoplaceorder);

        //radio group of cash or credit
        radioGroup = v.findViewById(R.id.rgroup);


        //when place order is clicked
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if no items is added to cart
                if (totalamounthere.getText().toString().equals("0")) {
                    Toast.makeText(getContext(), "Please add at least one item to cart", Toast.LENGTH_SHORT).show();

                } else {
                    //if payment mode is not selected
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getContext(), "Please select a payment mode!", Toast.LENGTH_LONG).show();
                    } else {
                        //if cart has items and payment mode is selected
                        databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //check user already ordered or not
                                if (snapshot.child("c_orders").child(username).hasChild("order_state")) {
                                    Toast.makeText(getActivity(), "Please wait until delivery of previous orders!", Toast.LENGTH_LONG).show();
                                } else {

                                    //adding total_amt to c_orders
                                    preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                    String username=preferences.getString("username","");
                                    DatabaseReference addtot=FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(username);
                                    addtot.child("total").setValue(totalamounthere.getText().toString());


                                    TextView itemname = v.findViewById(R.id.cart_name);


                                    DatabaseReference cartref = FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_orders");


                                    DatabaseReference order = FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_history").child(formattedDate).child("Items");

                                    //to move data from orders to order history of retailer
                                    moveFirebaseRecord(cartref, order);

                                    //check orders
                                    DatabaseReference c_cartref = FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_orders");


                                    DatabaseReference c_order = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(username).child("check_orders").child("Items");

                                    //to move data from orders to order recieved for client
                                    c_moveFirebaseRecord(c_cartref, c_order);

                                }

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
            }
        });

        return v;
    }

    //moves data from r_orders to c_orders
    private void c_moveFirebaseRecord(DatabaseReference c_cartref, DatabaseReference c_order) {
        c_cartref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                c_order.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            Toast.makeText(getContext(), "Not Successfull", Toast.LENGTH_LONG).show();
                        } else {

                            RadioButton select=getActivity().findViewById(radioGroup.getCheckedRadioButtonId());

                            String pmode1 = select== null ? "" : select.getText().toString();

                            preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                            String username=preferences.getString("username","");
                            DatabaseReference name=FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(username);
                            name.child("name").setValue(username);

                            DatabaseReference o_state=FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(username);
                            o_state.child("order_state").setValue("");


                            if(pmode1.equals("Cash")){


                                DatabaseReference mode=FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(username);
                                mode.child("pmode").setValue("Cash");

                            }else if(pmode1.equals("Credit")) {


                                DatabaseReference mode = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(username);
                                mode.child("pmode").setValue("Credit");

                            }



                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    //broadcast reciever recieves total amount from adapter
    public BroadcastReceiver msgbrdrec=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalofall=intent.getIntExtra("totalamount",0);
        String newtot=Integer.toString(totalofall);
        totalamounthere.setText(newtot);


            //visibility of no cart items text
            if (totalamounthere.getText().toString().equals("0")) {
                nocartitems.setVisibility(View.VISIBLE);
            }else{
                nocartitems.setVisibility(View.GONE);
            }

        }
    };



    //moves data from r_orders to r_history
    public void moveFirebaseRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(getContext(), "Not Successfull", Toast.LENGTH_LONG).show();
                        } else {

                            fromPath.removeValue();

                            RadioButton select=getActivity().findViewById(radioGroup.getCheckedRadioButtonId());

                            String pmode = select== null ? "" : select.getText().toString();
                            preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                            String username=preferences.getString("username","");

                            if(pmode.equals("Cash")){


                                DatabaseReference mode=FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_history");
                                mode.child(formattedDate).child("Pmode").setValue("Cash");
                                totalamounthere.setText("0");



                            }else if(pmode.equals("Credit")){


                                DatabaseReference mode=FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_history");
                                mode.child(formattedDate).child("Pmode").setValue("Credit");
                                totalamounthere.setText("0");

                            }
                            DatabaseReference date=FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_history").child(formattedDate);

                            date.child("date").setValue(formattedDate);

                            DatabaseReference  nameforfuture=FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_history").child(formattedDate);
                            nameforfuture.child("nameofretailer").setValue(username);


                            if(pmode.equals("Cash")){
                                tempmode="Pay on Delivery";
                            }else if(pmode.equals("Credit")){
                                tempmode="Your can pay in the future!";
                            }

                            //fragment transaction that order placed

                            AppCompatActivity appCompatActivity = (AppCompatActivity) getContext();
                            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.cart_retailer, new OrderPlacedFragment()).addToBackStack("placed").commit();


                            //notification that order placed

                            FirebaseDatabase.getInstance().getReference("Client").child("akashadeepa").child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    token=snapshot.getValue(String.class);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            //handle to delay in sending notification to owner
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    firebasenotificationsendertesting notificationsender=new firebasenotificationsendertesting(token,"Refill","New Order placed by "+username,"orderplaced",getContext() ,getActivity());
                                    notificationsender.sendnotifications();


                                }
                            },100);



                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();

            }
        });
    }


}