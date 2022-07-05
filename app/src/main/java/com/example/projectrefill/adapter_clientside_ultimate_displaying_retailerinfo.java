package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_clientside_ultimate_displaying_retailerinfo extends FirebaseRecyclerAdapter<client_model_ultimatedispofretinfo,adapter_clientside_ultimate_displaying_retailerinfo.myviewholder1> {

    public adapter_clientside_ultimate_displaying_retailerinfo(@NonNull FirebaseRecyclerOptions<client_model_ultimatedispofretinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder1 holder, int position, @NonNull client_model_ultimatedispofretinfo model) {
        holder.name.setText("Name: "+model.getName());
        holder.quan.setText("Quan: "+model.getQuan());
        holder.price.setText("Price: "+model.getPrice());
        holder.weight.setText("Weight(gms): "+model.getWeight());
        holder.tot.setText(model.getTotalamount());
    }

    @NonNull
    @Override
    public myviewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_client_ultimatedispofretinfo,parent,false);
        return new myviewholder1(view);
    }

    public class myviewholder1 extends RecyclerView.ViewHolder {
        TextView name,price,quan,tot,weight;

        public myviewholder1(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.datewisename);
            price=itemView.findViewById(R.id.datewiseprice);
            quan=itemView.findViewById(R.id.datewisequan);
            tot=itemView.findViewById(R.id.totalpricehere);
            weight=itemView.findViewById(R.id.datewiseweight);
        }
    }
}
