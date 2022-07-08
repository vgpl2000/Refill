package com.example.projectrefill;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class setting_profilepic_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button chng_image;
    ImageView profile,btn_close;

    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    Uri imageuri;

    public setting_profilepic_Fragment() {
        // Required empty public constructor
    }


    public static setting_profilepic_Fragment newInstance(String param1, String param2) {
        setting_profilepic_Fragment fragment = new setting_profilepic_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View v= inflater.inflate(R.layout.fragment_setting_profilepic_, container, false);

        btn_close=v.findViewById(R.id.btn_close_chng);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.change_profile,new SettingsFragment());
                fr.commit();
            }
        });


        profile=v.findViewById(R.id.imageViewfrprofilepic);

        //setting profile
        DatabaseReference pimag=FirebaseDatabase.getInstance().getReference("Client").child("akashadeepa");

        pimag.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("pimageurl")){
                    chng_image.setText("Update Profile Image");
                    String pi=snapshot.child("pimageurl").getValue(String.class);
                    Glide.with(profile.getContext()).load(pi).into(profile);
                }else {
                    chng_image.setText("Update Profile Image");
                    Glide.with(profile.getContext()).load(R.drawable.ic_baseline_person_outline_24).into(profile);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mgetcontent.launch("image/*");
            }
        });

        chng_image=v.findViewById(R.id.btn_chng_passwrd);

        chng_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(getActivity());

                dialog.setTitle("File uploader");
                dialog.show();

                StorageReference reference = firebaseStorage.getReference().child("ownerpimage").child("images/" + UUID.randomUUID().toString());
                if(imageuri==null){
                    Toast.makeText(getContext(), "Image not selected", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    reference.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {

                                dialog.dismiss();


                                Toast.makeText(getActivity(), "Image added successfully ", Toast.LENGTH_SHORT).show();



                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                fr.replace(R.id.change_profile, new SettingsFragment()).addToBackStack(null);
                                fr.commit();


                                Toast.makeText(getActivity(), "Please refresh to check new profile picture", Toast.LENGTH_LONG).show();
                                //toast


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


                                    DatabaseReference pi = FirebaseDatabase.getInstance().getReference("Client").child("akashadeepa");
                                    pi.child("pimageurl").setValue(uri.toString());







                                }
                            });
                        }
                    });

                }
            }
        });

      return v;
    }

    ActivityResultLauncher<String> mgetcontent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result!=null){
                profile.setImageURI(result);
                imageuri=result;
            }

        }
    });

}