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
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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


    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    adapter_retailerside_cart_display adapter;
    Button placeorder;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    retailer_model_placeorder_pressed model=null;
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
                    Toast toast = Toast.makeText(getActivity(), name1 + "can't use Refill right now!", Toast.LENGTH_SHORT);
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


        progressBar = v.findViewById(R.id.progressBarcart);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewtoshowcart);
        recyclerView.setLayoutManager(new CustomLinearLayoutManager1(getContext()));

        FirebaseRecyclerOptions<retailer_model_cart_retailer> options =
                new FirebaseRecyclerOptions.Builder<retailer_model_cart_retailer>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Retailer").child(username).child("r_orders"), retailer_model_cart_retailer.class)
                        .build();

        adapter = new adapter_retailerside_cart_display(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                progressBar.setVisibility(View.GONE);
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        placeorder = v.findViewById(R.id.buttontoplaceorder);

        radioGroup = v.findViewById(R.id.rgroup);


        databaseReference.child("Retailer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                final String getdueamt = snapshot.child(username).child("r_orders").child("Benne Murku").child("totalamount").getValue(String.class);

                try {
                    Integer tot = Integer.parseInt(getdueamt);
                    ftot = ftot + tot;
                    System.out.println(ftot + " some what values");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "good", Toast.LENGTH_SHORT).show();
            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalamounthere.getText().toString().equals("0")) {
                    Toast.makeText(getContext(), "Please add at least one item to cart", Toast.LENGTH_SHORT).show();

                } else {
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        Toast.makeText(getContext(), "Please select a payment mode!", Toast.LENGTH_LONG).show();
                    } else {

                        databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child("c_orders").child(username).hasChild("order_state")) {
                                    Toast.makeText(getActivity(), "Please wait until delivery of previous orders!", Toast.LENGTH_LONG).show();
                                } else {


                                    preferences = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                    String username = preferences.getString("username", "");


                                    TextView itemname = v.findViewById(R.id.cart_name);


                                    DatabaseReference cartref = FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_orders");


                                    DatabaseReference order = FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_history").child(formattedDate).child("Items");


                                    moveFirebaseRecord(cartref, order);

                                    //check orders
                                    DatabaseReference c_cartref = FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_orders");


                                    DatabaseReference c_order = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(username).child("check_orders").child("Items");

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
    public BroadcastReceiver msgbrdrec=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        int totalofall=intent.getIntExtra("totalamount",0);
        String newtot=Integer.toString(totalofall);
        totalamounthere.setText(newtot);


        }
    };




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

                                //Credit adding
                                DatabaseReference dataref=database.getReference("Retailer");
                                dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String due=snapshot.child(username).child("due_amt").getValue(String.class);
                                        Integer i_due=Integer.parseInt(due);
                                        String s_due=totalamounthere.getText().toString();
                                        Integer s_due1=Integer.parseInt(s_due);
                                        Integer f_due=i_due+s_due1;
                                        String final_due=Integer.toString(f_due);
                                        DatabaseReference dueref=dataref.child(username);
                                        dueref.child("due_amt").setValue(final_due);

                                        totalamounthere.setText("0");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                            DatabaseReference date=FirebaseDatabase.getInstance().getReference("Retailer").child(username).child("r_history").child(formattedDate);

                            date.child("date").setValue(formattedDate);

                            if(pmode.equals("Cash")){
                                tempmode="Pay on Delivery";
                            }else if(pmode.equals("Credit")){
                                tempmode="Your due amount is added!";
                            }

                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                            builder.setTitle("Order Placed!");
                            builder.setMessage("Your order is placed and will be accepted soon! \n"+tempmode);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();

                            //preparenotificationmessage(username);
                            String uid="akashadeepa";
                            sendnotification(uid,username);


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


    private  void sendnotification(String uid,String name){

        FirebaseDatabase.getInstance().getReference("Client").child("akashadeepa").child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                token=snapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                firebasenotificationsendertesting notificationsender=new firebasenotificationsendertesting(token,"Refill","New Order placed by "+name,"orderplaced",getContext() ,getActivity());
                notificationsender.sendnotifications();


            }
        },1000);
    }

    public void preparenotificationmessage(String orderId){
        System.out.println("inside prepare()");
        String NOTIFICATION_TOPIC="/topics/"+constantsforuse.FCM_KEY;
        String NOTIFICATION_TITILE="New Order from"+orderId;
        String NOTIFICATION_MESSAGE="New order is placed!";
        String NOTIFICATION_TYPE="neworder";

        SharedPreferences preferences;
        preferences = getContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String name1=preferences.getString("username","");
        String owner="akashadeepa";

        JSONObject notificationjo=new JSONObject();
        JSONObject notificationBodyjo=new JSONObject();
        try {
            System.out.println("inside prepare and try");
            notificationBodyjo.put("notificationtype",NOTIFICATION_TYPE);
            notificationBodyjo.put("buyeruid",name1);
            notificationBodyjo.put("selleruid",owner);
            notificationBodyjo.put("orderid",orderId);
            notificationBodyjo.put("notificationtitile",NOTIFICATION_TITILE);
            notificationBodyjo.put("notificationmessage",NOTIFICATION_MESSAGE);

            //where to send
            notificationjo.put("to",NOTIFICATION_TOPIC);
            notificationjo.put("data",notificationBodyjo);


        }catch (Exception e){
            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
       //sendfcmnotification(notificationjo,orderId);
    }

    private void sendfcmnotification(JSONObject notificationjo, String orderId) {
        System.out.println("inside sendfcmnotification");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", notificationjo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //after sending fcm start order details activity

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if failed sending fcm

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //put required headers
                Map<String,String> headers=new HashMap<>();
                headers.put("Content_Type","application/json");
                headers.put("Authorization","key="+constantsforuse.FCM_KEY);

                return headers;
            }
        };
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);

    }
}