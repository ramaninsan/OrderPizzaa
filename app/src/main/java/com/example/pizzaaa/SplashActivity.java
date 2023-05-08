package com.example.pizzaaa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appgozar.fadeoutparticle.FadeOutParticleFrameLayout;
import com.example.pizzaaa.appIntro.IntroActivity;
import com.example.pizzaaa.loginAndReg.LoginActivity;
import com.example.pizzaaa.utils.AppSession;
import com.example.pizzaaa.utils.Constants;

public class SplashActivity extends AppCompatActivity {

    //Declaring variables
    FadeOutParticleFrameLayout fadeOutParticleFrameLayout;
    boolean isLogin;
    boolean isLogout;
    boolean isFirstTime;
    AppSession session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // to hide action bar
        getSupportActionBar().hide();

        //instantiating variables
        session = new AppSession(this);

        // getting the boolean checks
        isLogin = session.getBoolenData(Constants.ISLOGIN);
        isLogout = session.getBoolenData(Constants.ISLOGOUT);
        isFirstTime = session.getBoolenData(Constants.ISFIRST);

        // image animation work
        fadeOutParticleFrameLayout = findViewById(R.id.fade_out_particle);
        fadeOutParticleFrameLayout.startAnimation();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                // This method will be executed once the timer is over

                if (isFirstTime){
                    if(isLogin) {
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                }


                if (isLogout){
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }



                if (!isFirstTime){
                    Intent i = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        }, 3000);


    }
}
