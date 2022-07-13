package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_client_setting_accp1 extends FirebaseRecyclerAdapter<client_model_setting_accp1,adapter_client_setting_accp1.myviewholder> {

    public adapter_client_setting_accp1(@NonNull FirebaseRecyclerOptions<client_model_setting_accp1> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull client_model_setting_accp1 model) {
        holder.rname.setText(model.getRname());

        //while retailer name is clicked on client side accepted orders
        holder.rname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frmlayoutclientaccp1,new settings_client_accp2_Fragment(model.getRname())).addToBackStack("client_acc").commit();


            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_setting_client_accp1,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        Button rname;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            rname=itemView.findViewById(R.id.datebutton);

        }
    }
}
