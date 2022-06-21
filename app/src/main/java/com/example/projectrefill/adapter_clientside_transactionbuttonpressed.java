package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_clientside_transactionbuttonpressed extends FirebaseRecyclerAdapter<client_model_todisplaytransactionswhenbuttonpressed,adapter_clientside_transactionbuttonpressed.myviewholder1> {

    public adapter_clientside_transactionbuttonpressed(@NonNull FirebaseRecyclerOptions<client_model_todisplaytransactionswhenbuttonpressed> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder1 holder, int position, @NonNull client_model_todisplaytransactionswhenbuttonpressed model) {
        holder.date.setText(model.getDate());
        holder.name.setText(model.getName());
        holder.quan.setText(model.getQuan());
    }

    @NonNull
    @Override
    public myviewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_design_transactionbuttonpressed,parent,false);
        return new myviewholder1(view);
    }

    public class myviewholder1 extends RecyclerView.ViewHolder {
        TextView date,name,quan;

        public myviewholder1(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datevalueherefortransactions);
            name=itemView.findViewById(R.id.namevalueherefortransaction);
            quan=itemView.findViewById(R.id.quanvalueherefortransaction);
        }
    }


    }
