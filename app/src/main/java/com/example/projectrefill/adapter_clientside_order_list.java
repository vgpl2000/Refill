package com.example.projectrefill;

import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class adapter_clientside_order_list extends FirebaseRecyclerAdapter<client_model_home_orders,adapter_clientside_order_list.myviewholder> implements Filterable
        {


            public adapter_clientside_order_list(@NonNull FirebaseRecyclerOptions<client_model_home_orders> options) {
                super(options);
            }

            @Override
            protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull client_model_home_orders model) {
                holder.textView.setText(model.getName());
                holder.btnchk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new Checkordersbtn_client_Fragment(model.getName())).addToBackStack(null).commit();
                    }
                });


                holder.btnacp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "accepting", Toast.LENGTH_SHORT).show();
                        String retailername, itemname, quan;

                        Date c = Calendar.getInstance().getTime();


                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(c).toString();
                        System.out.println("date to display for the system   " + formattedDate);

                        holder.btndel.setEnabled(true);
                        holder.btnacp.setEnabled(false);
                        holder.btncan.setEnabled(false);


                    }
                });


                holder.btncan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "cancelling", Toast.LENGTH_SHORT).show();
                        holder.btncan.setEnabled(false);
                        holder.btnacp.setEnabled(false);
                        holder.btndel.setEnabled(false);

                    }
                });


                holder.btndel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.btndel.setEnabled(false);
                        holder.btnacp.setEnabled(false);
                        holder.btncan.setEnabled(false);
                        Toast.makeText(view.getContext(), "delivered", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_order_clientside_homepage,parent,false);
               return new myviewholder(view);
            }

            @Override
            public Filter getFilter() {
                return null;
            }


            public class myviewholder extends RecyclerView.ViewHolder
             {
                    Button btnchk,btnacp,btncan,btndel;
                    TextView textView;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.retailer_name);
            btnchk=itemView.findViewById(R.id.btn_checkorders);
            btnacp=itemView.findViewById(R.id.btn_accept);
            btncan=itemView.findViewById(R.id.btn_cancl);
            btndel=itemView.findViewById(R.id.btn_deli);
        }

    }
}
