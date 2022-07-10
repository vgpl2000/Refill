package com.example.projectrefill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class onboard_Activity extends AppCompatActivity {
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button  skipbtn;

    TextView[] dots;
   adapter_viewpager_onboard viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboard);

        skipbtn = findViewById(R.id.skipbutton);

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(onboard_Activity.this,MainActivity.class);
                startActivity(i);
                finish();

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