package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Date;

public class adapter_retailerside_datedisplay extends FirebaseRecyclerAdapter<retailer_model_info_fragment_datedisplay,adapter_retailerside_datedisplay.myviewholder> {


    public adapter_retailerside_datedisplay(@NonNull FirebaseRecyclerOptions<retailer_model_info_fragment_datedisplay> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_info_fragment_datedisplay model) {
        holder.date.setText(model.getDate());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_date_display,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
            Button date;
            public myviewholder(@NonNull View itemView) {
                super(itemView);
                date=itemView.findViewById(R.id.datebutton);

            }
        }
    }
