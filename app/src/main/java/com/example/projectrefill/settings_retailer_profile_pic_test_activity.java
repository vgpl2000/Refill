package com.example.projectrefill;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class settings_retailer_profile_pic_test_activity extends AppCompatActivity {
    Button chng_image;
    ImageView profile1,btn_close;

    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    Uri imageuri1;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_retailer_profile_pic_test);



        preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String name1=preferences.getString("username","");

        btn_close=findViewById(R.id.btn_close_chng);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent=new Intent(settings_retailer_profile_pic_test_activity.this,retailer_activity.class);
               startActivity(intent);


            }
        });


        profile1=findViewById(R.id.imageViewfrprofilepic);

        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mgetcontent23.launch("image/*");
            }
        });

        chng_image=findViewById(R.id.btn_chng_passwrd);

        chng_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(settings_retailer_profile_pic_test_activity.this);

                dialog.setTitle("File uploader");
                dialog.show();

                StorageReference reference = firebaseStorage.getReference().child("retailerimages").child(name1).child("images/" + UUID.randomUUID().toString());
                if(imageuri1==null){
                    Toast.makeText(settings_retailer_profile_pic_test_activity.this, "Image not selected", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    reference.putFile(imageuri1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {

                                dialog.dismiss();


                                Toast.makeText(settings_retailer_profile_pic_test_activity.this, "Image added successfully ", Toast.LENGTH_SHORT).show();

                                //change to activity
                                Intent intent=new Intent(settings_retailer_profile_pic_test_activity.this,retailer_activity.class);
                                startActivity(intent);




                            } else {
                                dialog.dismiss();
                                Toast.makeText(settings_retailer_profile_pic_test_activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    });


                    reference.putFile(imageuri1).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            float per = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            dialog.setMessage("Uploading  " + (int) per + "%");


                        }
                    });


                    reference.putFile(imageuri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    DatabaseReference pi = FirebaseDatabase.getInstance().getReference("Retailer").child(name1);
                                    pi.child("pimageurl").setValue(uri.toString());

                                }
                            });
                        }
                    });

                }
            }
        });
    }
    ActivityResultLauncher<String> mgetcontent23=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result!=null){
                profile1.setImageURI(result);
                imageuri1=result;
            }


        }
    });
    @Override
    public void onBackPressed () {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            Intent intent = new Intent(settings_retailer_profile_pic_test_activity.this, retailer_activity.class);
            startActivity(intent);
            settings_retailer_profile_pic_test_activity.this.finish();
        } else {
            Intent intent = new Intent(settings_retailer_profile_pic_test_activity.this, retailer_activity.class);
            startActivity(intent);
            settings_retailer_profile_pic_test_activity.this.finish();
        }
    }
}