package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class onboard_Activity extends AppCompatActivity {
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button  skipbtn;
    LottieAnimationView lottie;


    TextView[] dots;
   adapter_viewpager_onboard viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboard);

        //preferences to add
        SharedPreferences preferences_o;
        SharedPreferences.Editor editor_o;
        preferences_o = getSharedPreferences("onboardPreferences", MODE_PRIVATE);
        editor_o = preferences_o.edit();
        //adding to preferences that onboard executed once
        editor_o.putString("onboard","executed");
        editor_o.commit();

        skipbtn = findViewById(R.id.skipbutton);
        lottie=findViewById(R.id.lottie_onboard);

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottie.setVisibility(View.VISIBLE);
                Thread thread=new Thread(){
                    public void run(){

                        try{
                            sleep(1000);

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        finally {
                            //intent
                            Intent i = new Intent(onboard_Activity.this,MainActivity.class);
                            startActivity(i);
                            finish();

                        }

                    }

                };thread.start();



            }
        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideviewpager);
        mDotLayout = (LinearLayout) findViewById(R.id.indication_layout);

        viewPagerAdapter = new adapter_viewpager_onboard(this);

        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpindicator(int position){

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.light_grey,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);

        }
        //to display login btn
        if(position==3){
            skipbtn.setText("Get Started !");
        }

        dots[position].setTextColor(getResources().getColor(R.color.yellow,getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){

        return mSLideViewPager.getCurrentItem() + i;
    }

}