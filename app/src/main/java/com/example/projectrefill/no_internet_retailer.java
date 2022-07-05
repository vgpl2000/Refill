package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class no_internet_retailer extends AppCompatActivity {

    Button r_noint;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_retailer);

        r_noint=findViewById(R.id.btn_r_noint);

        r_noint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(no_internet_retailer.this, retailer_activity.class);
                startActivity(intent);
            }
        });

    }


}