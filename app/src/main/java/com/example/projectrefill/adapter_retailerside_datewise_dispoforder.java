package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_retailerside_datewise_dispoforder extends FirebaseRecyclerAdapter<retailer_model_datewise_detailsdisp,adapter_retailerside_datewise_dispoforder.myviewholder> {

    public adapter_retailerside_datewise_dispoforder(@NonNull FirebaseRecyclerOptions<retailer_model_datewise_detailsdisp> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_datewise_detailsdisp model) {

      holder.name.setText("Name: "+model.getDate());




    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_design_retailerside_datewise_detailsdisp,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView name,price,quan,weight,totprice;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.datewisename);
            price=itemView.findViewById(R.id.datewiseprice);
            quan=itemView.findViewById(R.id.datewisequan);
            weight=itemView.findViewById(R.id.datewiseweight);
            totprice=itemView.findViewById(R.id.totalpricehere);

        }
    }
}
