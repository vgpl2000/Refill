package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_retailerside_cart_display extends FirebaseRecyclerAdapter<retailer_model_cart_retailer,adapter_retailerside_cart_display.myviewholder> {

    public adapter_retailerside_cart_display(@NonNull FirebaseRecyclerOptions<retailer_model_cart_retailer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_cart_retailer model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.quan.setText(model.getQuan());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_retailer_side_cart_displaying,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
    TextView name,price;
    EditText quan;
    Button update;
    ImageButton delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cart_name);
            price=itemView.findViewById(R.id.cart_price);
            quan=itemView.findViewById(R.id.cart_quan);
            update=itemView.findViewById(R.id.cart_updatequan);
            delete=itemView.findViewById(R.id.btndeleteincart);
        }
    }
}
