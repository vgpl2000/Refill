package com.example.projectrefill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import javax.xml.transform.Result;


public class client_btnplus_add_item_Fragment extends Fragment {




    //Declaration
    Button btn_add;
    ImageView btn_close;
    ImageView img;
    TextInputEditText i_name;
    TextInputEditText i_price;
    TextInputEditText i_weight;

    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    Uri imageuri;





    public client_btnplus_add_item_Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_client_btnplus_add_item_, container, false);

        img=v.findViewById(R.id.imageViewfritem);
        btn_add=v.findViewById(R.id.btn_add_item_client);
        btn_close=v.findViewById(R.id.btn_close);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mgetcontent.launch("image/*");

            }
        });


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.add_item,new ItemFragment());
                fr.commit();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i_name = v.findViewById(R.id.item_name);
                i_price = v.findViewById(R.id.item_price);
                i_weight = v.findViewById(R.id.item_weight);


                String str_iname = i_name.getText().toString();


                String str_iprice=i_price.getText().toString();

                ImageView img1=v.findViewById(R.id.imageViewfritem);


                //Integer str_iprice = Integer.parseInt(i_price.getText().toString());


                String str_iweight=i_weight.getText().toString();

                //Integer str_iweight = Integer.parseInt(i_weight.getText().toString());

                ProgressDialog dialog = new ProgressDialog(getActivity());

                dialog.setTitle("Image Uploading...");
                dialog.show();



                if(str_iname.isEmpty()||str_iprice.isEmpty()||str_iweight.isEmpty()) {
                    i_name.setError("Name cannot be empty");
                    i_price.setError("Price cannot be empty");
                    i_weight.setError("Weight cannot be empty");
                    dialog.dismiss();
                }else {

                    if(imageuri==null){
                        Toast.makeText(getContext(), "Image not selected", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else {

                        StorageReference reference = firebaseStorage.getReference().child("images/" + UUID.randomUUID().toString());

                        reference.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {

                                    dialog.dismiss();


                                    Toast.makeText(getActivity(), "Image added successfully", Toast.LENGTH_SHORT).show();


                                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                                    fr.replace(R.id.add_item, new ItemFragment()).addToBackStack(null);
                                    fr.commit();


                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        });


                        reference.putFile(imageuri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                float per = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                dialog.setMessage("Uploading  " + (int) per + "%");


                            }
                        });


                        reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String output1 = str_iname.substring(0, 1).toUpperCase() + str_iname.substring(1);

                                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                                        DatabaseReference root = db.getReference("Client").child("c_items");

                                        dataholder_for_additem_test obj1 = new dataholder_for_additem_test(str_iprice, str_iweight, output1, uri.toString());
                                        root.child(output1).setValue(obj1);

                                    }
                                });
                            }
                        });
                        reference.putFile(imageuri).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "No image is given", Toast.LENGTH_SHORT).show();
                            }
                        });
                        reference.putFile(imageuri).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "No image is given", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }


            }
        });



        return v;
    }


    ActivityResultLauncher<String> mgetcontent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result!=null){
                img.setImageURI(result);
                imageuri=result;
            }

        }
    });

}