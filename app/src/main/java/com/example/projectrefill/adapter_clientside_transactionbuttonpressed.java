package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adapter_clientside_transactionbuttonpressed extends FirebaseRecyclerAdapter<client_model_todispalldetailswhendatepressed,adapter_clientside_transactionbuttonpressed.myviewholder1> {

    public adapter_clientside_transactionbuttonpressed(@NonNull FirebaseRecyclerOptions<client_model_todispalldetailswhendatepressed> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder1 holder, int position, @NonNull client_model_todispalldetailswhendatepressed model) {
        //only date comes here
        holder.date.setText(model.getDate());
        holder.pmode.setText(model.getPmode());





        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrappernewforclient,new clientside_datebutton_pressed_detaildisp_Fragment(model.getDate(),model.getNameofretailer())).addToBackStack("r_trans").commit();

            }
        });

        holder.date.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.deletebtn.setVisibility(View.VISIBLE);
                return true;
            }
        });

        //to delete a transaction
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deleteref= FirebaseDatabase.getInstance().getReference("Retailer").child(model.getNameofretailer()).child("r_history");
                deleteref.child(model.getDate()).removeValue();
            }
        });

    }


    @NonNull
    @Override
    public myviewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_design_transactionbuttonpressed,parent,false);
        return new myviewholder1(view);
    }

    public class myviewholder1 extends RecyclerView.ViewHolder {
        TextView pmode,stext;
        Button date;
        ImageButton deletebtn;

        public myviewholder1(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datebutton);
            pmode=itemView.findViewById(R.id.paymentmodetodip);
            stext=itemView.findViewById(R.id.secrettextnooneknows);
            deletebtn=itemView.findViewById(R.id.delete_long_btn);

        }
    }


    }
