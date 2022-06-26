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
        holder.name.setText(model.getName());
        System.out.println(model.getName()+" name value");
        holder.price.setText(model.getPrice());
        System.out.println(model.getPrice()+" name value");
        holder.quan.setText(model.getQuan());
        System.out.println(model.getQuan()+" name value");
        holder.weight.setText(model.getWeight());
        System.out.println(model.getWeight()+" name value");

        String pri=holder.price.getText().toString();
        String qu=holder.quan.getText().toString();
        Integer pr1=Integer.parseInt(pri);
        Integer qr1=Integer.parseInt(qu);
        Integer tot=pr1*qr1;
        String totalf=Integer.toString(tot);
        holder.totprice.setText(totalf);
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
