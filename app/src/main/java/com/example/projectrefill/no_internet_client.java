package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class no_internet_client extends AppCompatActivity {

    Button c_noint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_client);

        c_noint=findViewById(R.id.btn_c_noint);

        c_noint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(no_internet_client.this, client_activity.class);
                startActivity(intent);
            }
        });
    }
}