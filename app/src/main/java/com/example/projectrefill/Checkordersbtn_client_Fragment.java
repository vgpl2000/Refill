package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Checkordersbtn_client_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();
    String name;
    RecyclerView recyclerView2;
    adapter_clientside_checkbtn adapter2;
    Button btnacp,btncan,btndel;
    String o_state;
    String token2;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
    String formattedDate1 = df.format(c);

    public Checkordersbtn_client_Fragment() {

    }
    public Checkordersbtn_client_Fragment(String name) {
        this.name=name;
    }
    public static Checkordersbtn_client_Fragment newInstance(String param1, String param2) {
        Checkordersbtn_client_Fragment fragment = new Checkordersbtn_client_Fragment();
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
        View view=inflater.inflate(R.layout.fragment_checkordersbtn_client_, container, false);

        TextView textView=view.findViewById(R.id.rname);
        textView.setText(name);
        String user=textView.getText().toString();

        btnacp=view.findViewById(R.id.btn_accept);
        btncan=view.findViewById(R.id.btn_cancl);
        btndel=view.findViewById(R.id.btn_deli);

        //to change the visibility of the buttons in orders received
        databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    o_state=snapshot.child("c_orders").child(name).child("order_state").getValue(String.class);
                    if(o_state.equals("accepted")){
                        btnacp.setVisibility(View.GONE);
                        btndel.setVisibility(View.VISIBLE);
                    }else if(o_state.equals("cancelled")){
                        btncan.setVisibility(View.GONE);
                       btnacp.setVisibility(View.GONE);
                        btndel.setVisibility(View.GONE);
                    }else if(o_state.equals("delivered")){
                        btndel.setVisibility(View.GONE);
                        btncan.setVisibility(View.GONE);
                        btnacp.setVisibility(View.GONE);
                    }else if(o_state.equals("")){
                        btnacp.setVisibility(View.VISIBLE);
                        btncan.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
        //setting recyclerview layout,adapter
        recyclerView2=(RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        //btn accept
        btnacp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //alert dialog while accepting orders
                android.app.AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Accept!");
                builder.setMessage("Are you sure you want to accept new order from "+name+" ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Accepting Order...", Toast.LENGTH_SHORT).show();
                        String retailername, itemname, quan;

                        Date c = Calendar.getInstance().getTime();

                        //recieving date of accepted order
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(c).toString();
                        System.out.println("date to display for the system   " + formattedDate);

                        btndel.setVisibility(View.VISIBLE);
                        btnacp.setVisibility(View.GONE);

                        //To set accepted order state value accepted

                        databaseReference.child("Client").child("c_orders").child(user).child("order_state").setValue("accepted");

                        String rname=name;


                        DatabaseReference fromp = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(rname).child("check_orders");


                        DatabaseReference top = FirebaseDatabase.getInstance().getReference("Client").child("c_accepted").child(rname).child("date").child(formattedDate1);

                        DatabaseReference fromforretailer = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(rname).child("check_orders");

                        DatabaseReference toretailer=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_accepted").child(formattedDate1);

                        //move from c_orders to c_accepted
                        fromp.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                top.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {


                                        Toast.makeText(view.getContext(), "Order Accepted", Toast.LENGTH_SHORT).show();

                                        DatabaseReference nameset=FirebaseDatabase.getInstance().getReference("Client").child("c_accepted").child(rname);
                                        nameset.child("rname").setValue(rname);

                                        DatabaseReference dateset=FirebaseDatabase.getInstance().getReference("Client").child("c_accepted").child(rname).child("date").child(formattedDate1);
                                        dateset.child("date").setValue(formattedDate1);

                                        dateset.child("retname").setValue(rname);

                                        DatabaseReference forpmode=FirebaseDatabase.getInstance().getReference("Client").child("c_orders");
                                        forpmode.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String mode=snapshot.child(rname).child("pmode").getValue(String.class);
                                                dateset.child("pmode").setValue(mode);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });




                                        //notification process
                                        SharedPreferences preferences;
                                        preferences = view.getContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                        String username=preferences.getString("username","");



                                        FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                token2=snapshot.getValue(String.class);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        //send notification to retailer when accepted
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                firebasenotificationsendertesting notificationsender2=new firebasenotificationsendertesting(token2,"Refill","Dear "+rname+" your order has been accepted!","accepted", getContext(),getActivity());
                                                notificationsender2.sendnotifications();


                                            }
                                        },100);



                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(view.getContext(), "Error while accepting", Toast.LENGTH_SHORT).show();
                            }
                        });

                        //from c_orders to r_accepted
                        fromforretailer.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                toretailer.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {


                                        DatabaseReference addref=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_accepted").child(formattedDate1);
                                         addref.child("date").setValue(formattedDate1);

                                        DatabaseReference forpmode=FirebaseDatabase.getInstance().getReference("Client").child("c_orders");
                                        forpmode.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String mode=snapshot.child(rname).child("pmode").getValue(String.class);
                                                addref.child("pmode").setValue(mode);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


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
        //when order is cancelled btn is clicked
        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rname=name;

                //displaying alert dialog while cancel is hit
                android.app.AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Cancel!");
                builder.setMessage("Are you sure you want to cancel new order from "+name+" ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Cancelling Order...", Toast.LENGTH_SHORT).show();

                        btncan.setVisibility(View.GONE);
                        btnacp.setVisibility(View.GONE);

                        //To update that order is cancelled in database
                        databaseReference.child("Client").child("c_orders").child(name).child("order_state").setValue("cancelled");




                        DatabaseReference fromp = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(rname).child("check_orders");


                        DatabaseReference top = FirebaseDatabase.getInstance().getReference("Client").child("c_cancelled").child(rname).child("date").child(formattedDate1);

                        //from c_orders to c_cancelled
                        fromp.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                top.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Order Cancelled", Toast.LENGTH_SHORT).show();

                                        //noti
                                        SharedPreferences preferences;
                                        preferences = view.getContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                                        String orderId=preferences.getString("username","");
                                        String owner="akashadeepa";


                                        FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                token2=snapshot.getValue(String.class);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        //to send notification
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                firebasenotificationsendertesting notificationsender2=new firebasenotificationsendertesting(token2,"Refill","Dear "+rname+" your order has been cancelled!","cancelled", getContext(),getActivity());
                                                notificationsender2.sendnotifications();


                                            }
                                        },100);

                                        //to replace fragment after notification and after cancelled
                                        Handler handler1=new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                                                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper2, new HomeFragment()).addToBackStack(null).commit();



                                            }
                                        },100);



                                        DatabaseReference nameset=FirebaseDatabase.getInstance().getReference("Client").child("c_cancelled").child(name);
                                        nameset.child("rname").setValue(name);

                                        DatabaseReference dateset=FirebaseDatabase.getInstance().getReference("Client").child("c_cancelled").child(name).child("date").child(formattedDate1);
                                        dateset.child("date").setValue(formattedDate1);

                                        dateset.child("retname").setValue(rname);

                                        DatabaseReference forpmode=FirebaseDatabase.getInstance().getReference("Client").child("c_orders");

                                        //to update pmode in c_orders inside that retailer
                                        forpmode.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String mode=snapshot.child(rname).child("pmode").getValue(String.class);
                                                dateset.child("pmode").setValue(mode);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });



                                        DatabaseReference addref=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_cancelled").child(formattedDate1);

                                        DatabaseReference forpmode2=FirebaseDatabase.getInstance().getReference("Client").child("c_orders");

                                        //setting pmode in c_orders
                                        forpmode2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String mode=snapshot.child(rname).child("pmode").getValue(String.class);
                                                addref.child("pmode").setValue(mode);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        //to remove c_orders of that retailer
                                        DatabaseReference frmtode=FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(rname);
                                        frmtode.removeValue();

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(view.getContext(), "Error cancelling", Toast.LENGTH_SHORT).show();
                            }
                        });




                        DatabaseReference fromforretailer2 = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(rname).child("check_orders");

                        DatabaseReference toretailer2=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_cancelled").child(formattedDate1);

                        fromforretailer2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                toretailer2.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                        DatabaseReference addref=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_cancelled").child(formattedDate1);
                                        addref.child("date").setValue(formattedDate1);


                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

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

        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Deliver!");
                builder.setMessage("Are you sure you want to update the delivery status of "+name+" ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                btndel.setVisibility(View.GONE);
                                databaseReference.child("Client").child("c_orders").child(name).child("order_state").setValue("delivered");
                                String rname=name;

                                //due adding
                                databaseReference.child("Client").child("c_orders").child(user).child("pmode").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String pmode=snapshot.getValue(String.class);
                                        if(pmode.equals("Credit")){
                                            //if credit then get totalamt
                                            databaseReference.child("Client").child("c_orders").child(user).child("total").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String totalamounthere=snapshot.getValue(String.class);
                                                    databaseReference.child("Retailer").child(user).child("due_amt").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            String due=snapshot.getValue(String.class);
                                                            Integer i_due=Integer.parseInt(due);
                                                            String s_due=totalamounthere;
                                                            Integer s_due1=Integer.parseInt(s_due);
                                                            Integer f_due=i_due+s_due1;
                                                            String final_due=Integer.toString(f_due);
                                                            DatabaseReference dueref=databaseReference.child("Retailer").child(user);
                                                            dueref.child("due_amt").setValue(final_due);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                DatabaseReference fromp = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(rname).child("check_orders");

                                DatabaseReference top = FirebaseDatabase.getInstance().getReference("Client").child("c_delivered").child(rname).child("date").child(formattedDate1);

                                //chekc_orders to c_delivered
                                fromp.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        top.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                Toast.makeText(view.getContext(), "Order Delivery Updated", Toast.LENGTH_SHORT).show();


                                                //noti
                                                FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        token2=snapshot.getValue(String.class);

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                //to send notification
                                                Handler handler=new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        System.out.println("inside postdelay");

                                                        firebasenotificationsendertesting notificationsender2=new firebasenotificationsendertesting(token2,"Refill","Dear "+rname+" your order has been delivered!","delivered", getContext(),getActivity());
                                                        notificationsender2.sendnotifications();


                                                    }
                                                },100);
                                                //this is to change fragment

                                                Handler handler1=new Handler();
                                                handler1.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                                                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper2, new HomeFragment()).addToBackStack(null).commit();



                                                    }

                                                },100);



                                                //to set retailer name in delivered
                                                DatabaseReference nameset=FirebaseDatabase.getInstance().getReference("Client").child("c_delivered").child(name);
                                                nameset.child("rname").setValue(name);

                                                //to set date in delivered
                                                DatabaseReference dateset=FirebaseDatabase.getInstance().getReference("Client").child("c_delivered").child(name).child("date").child(formattedDate1);
                                                dateset.child("date").setValue(formattedDate1);

                                                //to set retailer name
                                                dateset.child("retname").setValue(rname);

                                                DatabaseReference forpmode=FirebaseDatabase.getInstance().getReference("Client").child("c_orders");
                                                forpmode.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        //to set pmode
                                                        String mode=snapshot.child(rname).child("pmode").getValue(String.class);
                                                        dateset.child("pmode").setValue(mode);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });



                                                DatabaseReference addref=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_delivered").child(formattedDate1);

                                                DatabaseReference forpmode2=FirebaseDatabase.getInstance().getReference("Client").child("c_orders");
                                                forpmode2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        String mode=snapshot.child(rname).child("pmode").getValue(String.class);
                                                        addref.child("pmode").setValue(mode);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                                DatabaseReference frmtode=FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(rname);
                                                frmtode.removeValue();



                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(view.getContext(), "Error delivery pressed", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                DatabaseReference fromforretailer2 = FirebaseDatabase.getInstance().getReference("Client").child("c_orders").child(rname).child("check_orders");

                                DatabaseReference toretailer2=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_delivered").child(formattedDate1);

                                //check_orders to r_delivered
                                fromforretailer2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        toretailer2.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                DatabaseReference addref=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_delivered").child(formattedDate1);
                                                addref.child("date").setValue(formattedDate1);


                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



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



        String retailname=textView.getText().toString();

        //to display check orders
        FirebaseRecyclerOptions<client_model_btncheckorders> options2 =
                new FirebaseRecyclerOptions.Builder<client_model_btncheckorders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Client").child("c_orders").child(retailname).child("check_orders").child("Items"), client_model_btncheckorders.class)
                        .build();
        adapter2=new adapter_clientside_checkbtn(options2);
        recyclerView2.setAdapter(adapter2);


        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter2.stopListening();
    }

}