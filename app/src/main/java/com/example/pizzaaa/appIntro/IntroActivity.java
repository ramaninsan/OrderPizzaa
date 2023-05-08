package com.example.pizzaaa.appIntro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pizzaaa.R;
import com.example.pizzaaa.loginAndReg.LoginActivity;
import com.example.pizzaaa.utils.AppSession;
import com.example.pizzaaa.utils.Constants;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

public class IntroActivity extends AppCompatActivity {


    //Declaring variables
    ViewPager2 viewPager;
    TextView tvSkip, tvHeading, tvDesc;
    Button btnStart;
    int[] layouts;
    IntroSliderAdapter introSliderAdapter;
    Intent intent;
    IndicatorView dotsView;
    AppSession session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //to hide toolbar
        getSupportActionBar().hide();

        // calling method to instantiate items
        init();
    }

    private void init(){
        viewPager = findViewById(R.id.viewPager);
        tvHeading = findViewById(R.id.tvHeading);
        tvDesc = findViewById(R.id.tvDesc);
        tvSkip = findViewById(R.id.tvSkip);
        btnStart = findViewById(R.id.btnStart);
        dotsView = findViewById(R.id.dotsView);

        // Instantiating app session
        session = new AppSession(this);

        // setting intro screens
        layouts = new int[]{
                R.layout.intro_screen_1, R.layout.intro_screen_2, R.layout.intro_screen_3
        };


        introSliderAdapter = new IntroSliderAdapter(layouts);

        // setting the adapters
        viewPager.setAdapter(introSliderAdapter);

        // setting dots view
        dotsView.setSliderColor(Color.GRAY,Color.RED)
                .setSliderWidth(30).setSliderHeight(15)
                .setSlideMode(IndicatorSlideMode.WORM)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setupWithViewPager(viewPager);


        // calling view pager listener and updating screen items accordingly
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                dotsView.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                dotsView.onPageSelected(position);

                if (position == layouts.length-1){

                    tvHeading.setText(getResources().getString(R.string.homed));
                    btnStart.setVisibility(View.VISIBLE);
                    tvSkip.setVisibility(View.GONE);
                }
                else {
                    if (position == 0){
                        tvHeading.setText(getResources().getString(R.string.explore));
                        tvDesc.setText(getResources().getString(R.string.introdes));
                    } else {
                        tvHeading.setText(getResources().getString(R.string.enjoy));
                    }

                    btnStart.setVisibility(View.GONE);
                    tvSkip.setVisibility(View.VISIBLE);
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginScreen();
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginScreen();
            }
        });


    }



    private int getItem(int i){
        return viewPager.getCurrentItem() + i;
    }


    // method to move on to another screen
    private void launchLoginScreen() {
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        session.storeBoolenData(Constants.ISFIRST,true);
    }
}
