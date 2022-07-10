package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class adapter_clientside_retailerdetailesdisplying extends FirebaseRecyclerAdapter<client_model_fordisplayingretailerstoupdatedue,adapter_clientside_retailerdetailesdisplying.myviewholder> {
    //declaration of database to display retailers
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getInstance().getReference();
    String token2;

    private Context context;

    public adapter_clientside_retailerdetailesdisplying(@NonNull FirebaseRecyclerOptions<client_model_fordisplayingretailerstoupdatedue> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull client_model_fordisplayingretailerstoupdatedue model) {
        //setting due and name of the retailers
        holder.duefield.setText(model.getDue_amt());
        holder.name.setText(model.getName());


            String name2=holder.name.getText().toString();
            //check that retailer is blocked or not to display switch
            databaseReference.child("Retailer").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String name1=holder.name.getText().toString();
                    String state=snapshot.child(name1).child("state").getValue(String.class);

                    if(state.equals("blocked")){

                        holder.switchCompat.setChecked(true);

                    }else if(state.equals("notblocked")){
                        holder.switchCompat.setChecked(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            //setting profile
            DatabaseReference profile=FirebaseDatabase.getInstance().getReference("Retailer");
            profile.child(name2).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild("pimageurl")){
                        Glide.with(holder.imgprofile.getContext()).load(model.getPimageurl()).into(holder.imgprofile);
                    }else{
                        Glide.with(holder.imgprofile.getContext()).load(R.drawable.ic_baseline_person_outline_24).into(holder.imgprofile);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        //what happens when focus changes on due amt field
        holder.duefield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                holder.submit.setVisibility(View.VISIBLE);
            }
        });

        //on submitting new due_amt by client
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.duefield.getText().toString().isEmpty()) {
                    holder.duefield.setError("Cannot be empty!");
                } else {

                    holder.submit.setVisibility(View.GONE);
                    holder.duefield.clearFocus();
                    //to closekeyboard
                    InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    String name1 = holder.name.getText().toString();
                    String dueamt = holder.duefield.getText().toString();


                    HashMap user = new HashMap();
                    user.put("due_amt", dueamt);

                    //changing due amt in database
                    //FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Retailer");
                    databaseReference.child(name1).updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (task.isSuccessful()) {
                                holder.submit.setVisibility(View.GONE);

                                //send noti
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setTitle("Refill");
                                builder.setMessage("Do you want to send notification to " + model.getName() + " about the due of Rs." + dueamt + " ?");
                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //notification process

                                        FirebaseDatabase.getInstance().getReference("Retailer").child(model.getName()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                token2 = snapshot.getValue(String.class);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                firebasenotificationsendertesting notificationsender2 = new firebasenotificationsendertesting(token2, "Refill", "Dear " + model.getName() + ", Your new Due Amount to Akashadeepa is: Rs." + dueamt, "accepted", view.getContext(), (Activity) view.getContext());
                                                notificationsender2.sendnotifications();
                                                Toast.makeText(view.getContext(), "Notification sent!", Toast.LENGTH_SHORT).show();

                                            }
                                        }, 100);
                                    }


                                });
                                builder.show();

                            } else {
                                Toast.makeText(view.getContext(), "If its big error we will make updates", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        //when transaction btn is pressed of a particular retailer

        holder.trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change fragment which displays date wise r_history to click
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper5, new clientside_transactionpressedbutton_Fragment(model.getName())).addToBackStack("r_trans").commit();
            }
        });

        //to change the switch of any retailer is blocked out by client
        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                //blocked or not is updated in database for further use
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
                    hashstate.put("logstatus","loggedout");
                    

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

        //to send noti about due
        holder.retailer_name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(model.getDue_amt().equals("0")){
                    Toast.makeText(view.getContext(), "You cannot notify "+model.getName()+" when due amount is: "+model.getDue_amt(), Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Refill");
                    builder.setMessage("Do you want to send notification to " + model.getName() + " about the due of Rs." + model.getDue_amt() + " ?");
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //notification process

                            FirebaseDatabase.getInstance().getReference("Retailer").child(model.getName()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    token2 = snapshot.getValue(String.class);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    firebasenotificationsendertesting notificationsender2 = new firebasenotificationsendertesting(token2, "Refill", "Dear " + model.getName() + ", Amount of Rs." + model.getDue_amt() + " is due to Akashadeepa!", "accepted", view.getContext(), (Activity) view.getContext());
                                    notificationsender2.sendnotifications();
                                    Toast.makeText(view.getContext(), "Notification sent!", Toast.LENGTH_SHORT).show();

                                }
                            }, 100);
                        }


                    });
                    builder.show();
                }

                return true;
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
        CircleImageView imgprofile;
        TextView retailer_name;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.retailer_name);
            trans=itemView.findViewById(R.id.buttontoopentransactions);
            submit=itemView.findViewById(R.id.buttontosubmitdue);
            duefield=itemView.findViewById(R.id.edittexttoeditdueamount);
            switchCompat=itemView.findViewById(R.id.switchview);
            progressBar=itemView.findViewById(R.id.progressBarakasha);
            imgprofile=itemView.findViewById(R.id.imgprofile);
            retailer_name=itemView.findViewById(R.id.retailer_name);


        }
    }
}
