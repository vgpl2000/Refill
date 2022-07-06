package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_retailer_setting_delivere1 extends FirebaseRecyclerAdapter<retailer_model_setting_accp1,adapter_retailer_setting_delivere1.myviewholder> {

    public adapter_retailer_setting_delivere1(@NonNull FirebaseRecyclerOptions<retailer_model_setting_accp1> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_setting_accp1 model) {
        holder.date.setText(model.getDate());
        holder.mode.setText(model.getPmode());
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frmlayoutdelivere1,new retailer_setting_deliver2_Fragment(model.getDate())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_design_retailer_setting_accp1,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        Button date;
        TextView mode;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datebutton);
            mode=itemView.findViewById(R.id.paymentmodetodip);
        }
    }
}
