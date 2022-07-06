package com.example.projectrefill;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_retailer_setting_accp1 extends FirebaseRecyclerAdapter<retailer_model_setting_accp1,adapter_retailer_setting_accp1.myviewholder> {


    public adapter_retailer_setting_accp1(@NonNull FirebaseRecyclerOptions<retailer_model_setting_accp1> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_setting_accp1 model) {
        holder.date.setText(model.getDate());
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), holder.date.getText().toString()+" this date pressed", Toast.LENGTH_SHORT).show();
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

        public myviewholder(@NonNull View itemView) {

            super(itemView);
            date=itemView.findViewById(R.id.datebutton);
        }
    }
}
