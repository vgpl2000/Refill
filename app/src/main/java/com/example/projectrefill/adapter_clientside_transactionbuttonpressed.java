package com.example.projectrefill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class adapter_clientside_transactionbuttonpressed extends FirebaseRecyclerAdapter<client_model_todispalldetailswhendatepressed,adapter_clientside_transactionbuttonpressed.myviewholder1> {

    public adapter_clientside_transactionbuttonpressed(@NonNull FirebaseRecyclerOptions<client_model_todispalldetailswhendatepressed> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder1 holder, int position, @NonNull client_model_todispalldetailswhendatepressed model) {
        //only date comes here
        holder.date.setText(model.getDate());
        holder.pmode.setText(model.getPmode());





        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrappernewforclient,new clientside_datebutton_pressed_detaildisp_Fragment(model.getDate(),model.getNameofretailer())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_design_transactionbuttonpressed,parent,false);
        return new myviewholder1(view);
    }

    public class myviewholder1 extends RecyclerView.ViewHolder {
        TextView pmode,stext;
        Button date;

        public myviewholder1(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datebutton);
            pmode=itemView.findViewById(R.id.paymentmodetodip);
            stext=itemView.findViewById(R.id.secrettextnooneknows);

        }
    }


    }
