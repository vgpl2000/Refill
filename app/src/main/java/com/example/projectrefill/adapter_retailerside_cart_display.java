package com.example.projectrefill;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class adapter_retailerside_cart_display extends FirebaseRecyclerAdapter<retailer_model_cart_retailer,adapter_retailerside_cart_display.myviewholder> {

    Integer totalprice=0;

    public adapter_retailerside_cart_display(@NonNull FirebaseRecyclerOptions<retailer_model_cart_retailer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_cart_retailer model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.quan.setText(model.getQuan());
        String price2=holder.price.getText().toString();
        String quan2=holder.quan.getText().toString();
        Integer pr,qu,tot;
        pr=Integer.parseInt(price2);
        qu=Integer.parseInt(quan2);
        tot=pr*qu;
        String totalamt=Integer.toString(tot);


        System.out.println(totalprice+" this is the total of all items");


        holder.totalamtofitem.setText(totalamt);



        holder.quan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                holder.update.setVisibility(View.VISIBLE);

            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.update.setVisibility(View.GONE);
                holder.quan.clearFocus();

                String quannewvalue;
                quannewvalue=holder.quan.getText().toString();
                String iname=holder.name.getText().toString();

                HashMap user=new HashMap();
                user.put("quan",quannewvalue);
                user.put("totalamount",totalamt);

                String rname=holder.stext.getText().toString();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Retailer");
                databaseReference.child(rname).child("r_orders").child(iname).updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {


                        if (task.isSuccessful()){

                            holder.update.setVisibility(View.GONE);
                            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        }else {
                            Toast.makeText(view.getContext(), "If its big error we will make updates", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rname=holder.stext.getText().toString();
                String iname=holder.name.getText().toString();

                DatabaseReference remove=FirebaseDatabase.getInstance().getReference("Retailer").child(rname).child("r_orders").child(iname);
                remove.removeValue();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_retailer_side_cart_displaying,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {
    TextView name,price,stext;
    EditText quan;
    Button update;
    ImageButton delete;
    TextView totalamtofitem;
    Integer totalofallitems=0;
    TextView totalvalue;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cart_name);
            price=itemView.findViewById(R.id.cart_price);
            quan=itemView.findViewById(R.id.cart_quan);
            update=itemView.findViewById(R.id.cart_updatequan);
            delete=itemView.findViewById(R.id.btndeleteincart);
            SharedPreferences prefs = itemView.getContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
            String ipAdrs=prefs.getString("username", "");
            stext=itemView.findViewById(R.id.secrettext2);
            stext.setText(ipAdrs);
            totalamtofitem=itemView.findViewById(R.id.singleitemtotalamt);


        }
    }
}
