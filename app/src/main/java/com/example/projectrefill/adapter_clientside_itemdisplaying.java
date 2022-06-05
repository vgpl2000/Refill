package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_clientside_itemdisplaying extends FirebaseRecyclerAdapter<client_model_todisplayitemsavailable,adapter_clientside_itemdisplaying.myviewholderfordisplaying> {

    public adapter_clientside_itemdisplaying(@NonNull FirebaseRecyclerOptions<client_model_todisplayitemsavailable> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholderfordisplaying holder, int position, @NonNull client_model_todisplayitemsavailable model) {
        holder.nm.setText(model.getName());
    holder.pri.setText(model.getPrice());
    holder.qty.setText(model.getQuan());
    Glide.with(holder.img1.getContext()).load(model.getUrl()).into(holder.img1);
    holder.weight.setText(model.getWeight());

    }

    @NonNull
    @Override
    public myviewholderfordisplaying onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_itemdisplayingforclient,parent,false);
        return new myviewholderfordisplaying(view);
    }

    public class myviewholderfordisplaying extends RecyclerView.ViewHolder{
        ImageView img1;
        TextView nm,pri,qty,weight;

        public myviewholderfordisplaying(@NonNull View itemView) {
            super(itemView);
            img1=itemView.findViewById(R.id.imageViewfordisplayingitems);
            nm=itemView.findViewById(R.id.namevalueforitem);
            pri=itemView.findViewById(R.id.pricevalueforitem);
            qty=itemView.findViewById(R.id.quantvalueforitem);
            weight=itemView.findViewById(R.id.weightvalueforitem);

        }
    }
}
