package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_retailerside_datewise_dispoforder extends FirebaseRecyclerAdapter<retailer_model_datewise_dispwhenpressed,adapter_retailerside_datewise_dispoforder.myviewholder> {

    public adapter_retailerside_datewise_dispoforder(@NonNull FirebaseRecyclerOptions<retailer_model_datewise_dispwhenpressed> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_datewise_dispwhenpressed model) {
        //orders made on the clicked date retailer side
      holder.name.setText("Name: "+model.getName());
      holder.price.setText("Price: "+model.getPrice());
      holder.quan.setText("Quan: "+model.getQuan());
      holder.totprice.setText(model.getTotalamount());
      holder.weight.setText("Weight(gms): "+model.getWeight());


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_retailer_datewise_whenpressed,parent,false);
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
