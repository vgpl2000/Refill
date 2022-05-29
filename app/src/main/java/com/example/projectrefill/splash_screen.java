package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class splash_screen extends AppCompatActivity {

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        Thread thread=new Thread(){
            public void run(){
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