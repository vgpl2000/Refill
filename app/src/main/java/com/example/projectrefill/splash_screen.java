package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {
    TextView title_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        title_text=findViewById(R.id.title_text);

        Thread thread=new Thread(){
            public void run(){
                Animation animation= AnimationUtils.loadAnimation(splash_screen.this,R.anim.anim);
                title_text.startAnimation(animation);
                try{
                    sleep(3000);


                    }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(splash_screen.this,MainActivity.class);
                    startActivity(intent);

                }

                }

            };thread.start();
        }

}