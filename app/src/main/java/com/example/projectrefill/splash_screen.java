package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen extends AppCompatActivity {
    TextView title_text,textView2;
    ImageView imageView;

    //method to perform backpressed to navigate to home screen of phone
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To remove notification bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        title_text=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);

        //stay in splash screen for 3000ms(3sec)
        Thread thread=new Thread(){
            public void run(){
                Animation animation= AnimationUtils.loadAnimation(splash_screen.this,R.anim.anim);
                Animation animation2= AnimationUtils.loadAnimation(splash_screen.this,R.anim.fade_in);
                title_text.startAnimation(animation);
                textView2.startAnimation(animation2);
                imageView=findViewById(R.id.imageView4);
                imageView.startAnimation(animation2);


                try{
                    sleep(3000);


                    }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally {
                    //after 3 sec
                    SharedPreferences preferences_o;
                    preferences_o = getSharedPreferences("onboardPreferences", MODE_PRIVATE);

                    //if onboarding is not already shown
                    if (preferences_o.contains("onboard")) {
                        Intent intent=new Intent(splash_screen.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        //if onboarding is shown, redirect to login screen
                        Intent intent=new Intent(splash_screen.this,onboard_Activity.class);
                        startActivity(intent);
                    }


                }

                }

            };thread.start();
        }

}