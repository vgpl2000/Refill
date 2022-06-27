package com.example.projectrefill;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class adapter_clientside_retailerdetailesdisplying extends FirebaseRecyclerAdapter<client_model_fordisplayingretailerstoupdatedue,adapter_clientside_retailerdetailesdisplying.myviewholder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();

    private Context context;

    public adapter_clientside_retailerdetailesdisplying(@NonNull FirebaseRecyclerOptions<client_model_fordisplayingretailerstoupdatedue> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull client_model_fordisplayingretailerstoupdatedue model) {
        holder.duefield.setText(model.getDue_amt());
        holder.name.setText(model.getName());

        //check that retailer is blocked or not to display switch
        databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name1=holder.name.getText().toString();
                String state=snapshot.child(name1).child("state").getValue(String.class);

                if(state.equals("blocked")){

                    holder.switchCompat.setChecked(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.duefield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                holder.submit.setVisibility(View.VISIBLE);
            }
        });
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.submit.setVisibility(View.GONE);
                holder.duefield.clearFocus();


                String name1=holder.name.getText().toString();
                String dueamt=holder.duefield.getText().toString();


                HashMap user=new HashMap();
                user.put("due_amt",dueamt);


                //FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Retailer");
                databaseReference.child(name1).updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {


                        if (task.isSuccessful()){
                            holder.submit.setVisibility(View.GONE);


                            
                          }else {
                            Toast.makeText(view.getContext(), "If its big error we will make updates", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //holder.name2.setText(model.getName());
        holder.trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper5, new clientside_transactionpressedbutton_Fragment(model.getName())).addToBackStack(null).commit();
            }
        });

        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //getting needed strings to pass


                if(b){
                    holder.progressBar.setVisibility(View.VISIBLE);
                    String name1=holder.name.getText().toString();
                    String state="blocked";
                    HashMap hashstate=new HashMap();
                    hashstate.put("state",state);


                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Retailer");
                    databaseReference.child(name1).updateChildren(hashstate).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (task.isSuccessful()){
                                holder.progressBar.setVisibility(View.GONE);

                            }
                        }
                    });
                }else{
                    holder.progressBar.setVisibility(View.VISIBLE);
                    String state="notblocked";
                    String name1=holder.name.getText().toString();
                    HashMap hashstate=new HashMap();
                    hashstate.put("state",state);
                    

                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Retailer");
                    databaseReference.child(name1).updateChildren(hashstate).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {


                            if (task.isSuccessful()){
                                holder.progressBar.setVisibility(View.GONE);

                            }
                        }
                    });

                }


            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_design_fordisplayingretailerlistwithdueforclient,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        TextView name;
        Button trans,submit;
        EditText duefield;
        SwitchCompat switchCompat;
        ProgressBar progressBar;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.retailer_name);
            trans=itemView.findViewById(R.id.buttontoopentransactions);
            submit=itemView.findViewById(R.id.buttontosubmitdue);
            duefield=itemView.findViewById(R.id.edittexttoeditdueamount);
            switchCompat=itemView.findViewById(R.id.switchview);
            progressBar=itemView.findViewById(R.id.progressBarakasha);
            //name2=itemView.findViewById(R.id.textViewtodispnamefortrans);

        }
    }
}
