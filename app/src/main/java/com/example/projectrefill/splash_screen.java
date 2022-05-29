package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {
    TextView title_text,textView2;
ImageView imageView,imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        title_text=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        imageView=findViewById(R.id.imageView6);
        imageView2=findViewById(R.id.imageView5);

        Thread thread=new Thread(){
            public void run(){
                Animation animation= AnimationUtils.loadAnimation(splash_screen.this,R.anim.anim);
                Animation animation2= AnimationUtils.loadAnimation(splash_screen.this,R.anim.fade_in);
                title_text.startAnimation(animation);
                textView2.startAnimation(animation2);
                imageView2.startAnimation(animation2);
                imageView.startAnimation(animation2);

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