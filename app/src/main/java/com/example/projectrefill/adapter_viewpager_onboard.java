package com.example.projectrefill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;

public class adapter_viewpager_onboard extends PagerAdapter {

    Context context;
    //lottie animation files in onboarding
    int images[]={
      R.raw.onboard_realtime,
            R.raw.onboard_notification,
            R.raw.onboard_security,
            R.raw.onbaord_anywhere
    };
    //headings of onboarding
    int headings[] = {

            R.string.heading_one,
            R.string.heading_two,
            R.string.heading_three,
            R.string.heading_fourth
    };
    //description of each page of onboarding
    int description[] = {

            R.string.desc_one,
            R.string.desc_two,
            R.string.desc_three,
            R.string.desc_fourth
    };

    public adapter_viewpager_onboard(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
       return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout)object;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //setting the predefined layout file
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        //setting image, title and description of each page on onboarding
        LottieAnimationView slidetitleimage = (LottieAnimationView) view.findViewById(R.id.titleimage);
        TextView slideHeading = (TextView) view.findViewById(R.id.texttitle);
        TextView slideDesciption = (TextView) view.findViewById(R.id.textdescription);

        slidetitleimage.setAnimation(images[position]);
        slideHeading.setText(headings[position]);
        slideDesciption.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
