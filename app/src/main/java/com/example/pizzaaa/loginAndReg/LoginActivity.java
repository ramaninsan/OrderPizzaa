package com.example.pizzaaa.loginAndReg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.pizzaaa.MainActivity;
import com.example.pizzaaa.R;
import com.example.pizzaaa.SplashActivity;
import com.example.pizzaaa.utils.AppSession;
import com.example.pizzaaa.utils.Constants;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {



//Declaring variables
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;
    AppSession session;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hiding the action bar
        getSupportActionBar().hide();

        // calling method in which variables initialized
        init();
    }


    private void init(){
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        coordinatorLayout = findViewById(R.id.cl);

        session = new AppSession(this);

        // button click listeners
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // validation of fields
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!email.equals(session.getStringData(Constants.EMAIL))){
                    showError(getResources().getString(R.string.error),getResources().getString(R.string.regemail));
                    return;
                }

                if (!password.equals(session.getStringData(Constants.PASSWORD))){
                    showError(getResources().getString(R.string.error),getResources().getString(R.string.regpass));
                    return;
                }

                openAnotherActivity(MainActivity.class);
                session.storeBoolenData(Constants.ISLOGIN,true);
            }
        });


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAnotherActivity(RegisterActivity.class);
            }
        });
    }


    // method to open desire activity
    private void openAnotherActivity(Class myClass){
        Intent intent = new Intent(LoginActivity.this, myClass);
        startActivity(intent);
        finish();
    }


    // method to show error in a snackbar
    private void showError(String mTitle, String message){

        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();

    }
}
