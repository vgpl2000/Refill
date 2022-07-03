package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Date;

public class adapter_retailerside_datedisplay extends FirebaseRecyclerAdapter<retailer_model_info_fragment_datedisplay,adapter_retailerside_datedisplay.myviewholder> {

    String datefinal;
    TextView secret;

    public adapter_retailerside_datedisplay(@NonNull FirebaseRecyclerOptions<retailer_model_info_fragment_datedisplay> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_info_fragment_datedisplay model) {

        holder.date.setText("Mode: "+model.getPmode()+"/"+ model.getDate());
        datefinal=holder.date.getText().toString();
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AppCompatActivity appCompatActivity=(AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper7,new retailerside_datewisetransaction_Fragment(model.getDate())).addToBackStack(null).commit();

            }
        });
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
