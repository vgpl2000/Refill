package com.example.projectrefill;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.internal.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class adapter_retailerside_homepage_itemdisplay extends FirebaseRecyclerAdapter<retailer_model_home_recycler_itemdisplaying,adapter_retailerside_homepage_itemdisplay.myviewholder>
    {
        static Integer a=0;

        public adapter_retailerside_homepage_itemdisplay(@NonNull FirebaseRecyclerOptions<retailer_model_home_recycler_itemdisplaying> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull retailer_model_home_recycler_itemdisplaying model) {
            //set name,price,weight
            holder.name.setText(model.getName());
            holder.price.setText(model.getPrice());
            holder.weight.setText(model.getWeight()+"gms");

            //to display image of item
            DatabaseReference imageref= FirebaseDatabase.getInstance().getReference("Client").child("c_items").child(model.getName());
            imageref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild("url")){
                        Glide.with(holder.imageView.getContext()).load(model.getUrl()).into(holder.imageView);
                    }else{
                        Glide.with(holder.imageView.getContext()).load(R.drawable.ic_baseline_image_24).into(holder.imageView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            //when quantity edittext is clicked, make visible the add to cart button
          holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
              @Override
              public void onFocusChange(View view, boolean b) {
                  holder.cart.setVisibility(View.VISIBLE);
              }
          });

            //when add to cart is clicked
            holder.cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.editText.getText().toString().isEmpty()) {
                        holder.editText.setError("Quantity cannot be empty!");
                    } else {


                    Date c = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);


                    String quan = holder.editText.getText().toString();


                    String iname = holder.name.getText().toString();
                    String price = holder.price.getText().toString();
                    String weight = holder.weight.getText().toString();

                    String rname = holder.stext.getText().toString();

                    //total of each items
                    Integer p, q, tot;
                    p = Integer.parseInt(price);
                    q = Integer.parseInt(quan);
                    tot = p * q;
                    String total = Integer.toString(tot);

                    //when no quan is entered
                    if (quan.isEmpty() || quan.equals("0")) {
                        holder.editText.setError("Specify the quantity");
                    } else {

                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference("Retailer").child(rname).child("r_orders");

                        a = a + 1;
                        String b = Integer.toString(a);

                        retailer_model_button_addtocart_pressed obj1 = new retailer_model_button_addtocart_pressed(iname, quan, weight, formattedDate, price, total);
                        root.child(iname).setValue(obj1);


                        Toast.makeText(view.getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                        holder.editText.setText("");
                        holder.editText.clearFocus();
                        //close keyboard
                        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        holder.cart.setVisibility(View.GONE);

                    }
                }
            }
            });
        }

        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design_retailerside_homepage_itemdisplay,parent,false);

            return new myviewholder(view);
        }

        public class myviewholder extends RecyclerView.ViewHolder{
        TextView name,price,weight,stext;
        ImageView imageView;
        EditText editText;
        Button cart;

        //hellooooo



        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.namevalueforitem);
            price=itemView.findViewById(R.id.pricevalueforitem);
            weight=itemView.findViewById(R.id.weightvalueforitem);
            imageView=itemView.findViewById(R.id.imageViewfordisplayingitems);
            editText=itemView.findViewById(R.id.edittextforhomeretailertoenterquan);
            cart=itemView.findViewById(R.id.btneditforitemdisplay);
            SharedPreferences prefs = itemView.getContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
            String ipAdrs=prefs.getString("username", "");
            stext=itemView.findViewById(R.id.secrettext);
            stext.setText(ipAdrs);

        }

    }

}
