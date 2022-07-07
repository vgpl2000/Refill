package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.internal.ParcelableSparseBooleanArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class adapter_clientside_order_list extends FirebaseRecyclerAdapter<client_model_home_orders,adapter_clientside_order_list.myviewholder> implements Filterable
        {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getInstance().getReference();
            String o_state;
            ProgressBar progressBar;
            Date c = Calendar.getInstance().getTime();
            String token2;

            //declaration for notifications
            private static  final  String CHANNEL_ID="My Channel";
            private static  final  int REQUEST_CODE=100;
            private static  final  int NOTIFICATION_ID=100;

            //date to use later but never used
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
            String formattedDate1 = df.format(c);


            @Override
            public int getItemCount() {
                return super.getItemCount();
            }

            public adapter_clientside_order_list(@NonNull FirebaseRecyclerOptions<client_model_home_orders> options) {

                super(options);
            }


            @Override
            protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull client_model_home_orders model) {

                //to change the state of button after clicking accept,cancel and delivered(loads from database)
                databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            o_state = snapshot.child("c_orders").child(model.getName()).child("order_state").getValue(String.class);
                            if (o_state.equals("accepted")) {
                                holder.btnacp.setVisibility(View.GONE);
                                holder.btncan.setVisibility(View.GONE);
                                holder.btndel.setVisibility(View.VISIBLE);
                            } else if (o_state.equals("cancelled")) {
                                holder.btncan.setVisibility(View.GONE);
                                holder.btnacp.setVisibility(View.GONE);
                                holder.btndel.setVisibility(View.GONE);
                            } else if (o_state.equals("delivered")) {
                                holder.btndel.setVisibility(View.GONE);
                                holder.btncan.setVisibility(View.GONE);
                                holder.btnacp.setVisibility(View.GONE);
                            } else if (o_state.equals("")) {
                                holder.btnacp.setVisibility(View.VISIBLE);
                                holder.btncan.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });

                //to set name of retailer when recieved order
                holder.textView.setText(model.getName());
                holder.btnchk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new Checkordersbtn_client_Fragment(model.getName())).addToBackStack("home_orders").commit();
                    }
                });

                //to set payment mode of recieved orders
                holder.modep.setText(model.getPmode());
                holder.total.setText(model.getTotal());

            }

            @NonNull

            @Override
            public Filter getFilter() {
                return null;
            }

            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_order_clientside_homepage,parent,false);


                return new myviewholder(view);

            }


            public class myviewholder extends RecyclerView.ViewHolder
             {
                    Button btnchk,btnacp,btncan,btndel;
                    TextView textView,modep,total;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            //findviewbyid
            textView=itemView.findViewById(R.id.retailer_name);
            btnchk=itemView.findViewById(R.id.btn_checkorders);
            btnacp=itemView.findViewById(R.id.btn_accept);
            btncan=itemView.findViewById(R.id.btn_cancl);
            btndel=itemView.findViewById(R.id.btn_deli);
            progressBar=itemView.findViewById(R.id.progressBar);
            modep=itemView.findViewById(R.id.pmodeforclient);
            total=itemView.findViewById(R.id.totalamt);
        }



    }//code which was used to copy the database records from one branch to the other
            /*private void c_moveFirebaseRecord(DatabaseReference fromp, final DatabaseReference top) {
                fromp.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        top.setValue(snapshot.getValue(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(, "accepted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }*/


        }
