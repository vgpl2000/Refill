package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_retailer_setting_accp2 extends FirebaseRecyclerAdapter<retailer_model_setting_accp2,adapter_retailer_setting_accp2.myviewholder> {

    public adapter_retailer_setting_accp2(@NonNull FirebaseRecyclerOptions<retailer_model_setting_accp2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_setting_accp2 model) {
        holder.name.setText("Name: "+model.getName());
        holder.price.setText("Price: "+model.getPrice());
        holder.weight.setText("Weight(gms): "+model.getWeight());
        holder.quan.setText("Quan: "+model.getQuan());
        holder.tot.setText(model.getTotalamount());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_design_retailer_setting_accp2,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        TextView name,price,quan,tot,weight;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
          name=itemView.findViewById(R.id.datewisename);
          price=itemView.findViewById(R.id.datewiseprice);
          quan=itemView.findViewById(R.id.datewisequan);
          tot=itemView.findViewById(R.id.totalpricehere);
          weight=itemView.findViewById(R.id.datewiseweight);

        }
    }
}
