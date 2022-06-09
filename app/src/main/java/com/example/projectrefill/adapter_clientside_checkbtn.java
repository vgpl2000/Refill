package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_clientside_checkbtn extends FirebaseRecyclerAdapter<client_model_btncheckorders,adapter_clientside_checkbtn.myviewholder1> {

    public adapter_clientside_checkbtn(@NonNull FirebaseRecyclerOptions<client_model_btncheckorders> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder1 holder, int position, @NonNull client_model_btncheckorders model) {
        holder.dt.setText(model.getDate());
        holder.nm.setText(model.getItem());
        holder.qu.setText(model.getQuan());
    }

    @NonNull
    @Override
    public myviewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_checkorders_clientside,parent,false);
        return new myviewholder1(view);
    }

    public class myviewholder1 extends RecyclerView.ViewHolder{
        TextView dt,nm,qu;


        public myviewholder1(@NonNull View itemView) {
            super(itemView);
            dt=(TextView) itemView.findViewById(R.id.datevaluehere);
            nm=(TextView) itemView.findViewById(R.id.namevaluehere);
            qu=(TextView) itemView.findViewById(R.id.quanvaluehere);
        }
    }

}
