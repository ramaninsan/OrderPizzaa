package com.example.pizzaaa.loginAndReg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


import com.example.pizzaaa.MainActivity;
import com.example.pizzaaa.R;
import com.example.pizzaaa.utils.AppSession;
import com.example.pizzaaa.utils.Constants;
import com.google.android.material.snackbar.Snackbar;


public class RegisterActivity extends AppCompatActivity {


    ////Declaring variables
    EditText etName, etEmail, etPassword;
    AppCompatButton btnRegister;
    ImageView imgBack;
    AppSession session;
    RelativeLayout rootLayout;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // hiding the action bar
        getSupportActionBar().hide();

        // calling method in which variables initialized
        init();

    }

    private void init(){
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnSignup);
        imgBack = findViewById(R.id.imgBack);
        rootLayout = findViewById(R.id.rootLayout);
        coordinatorLayout = findViewById(R.id.cl);
        session = new AppSession(this);

        // button click listeners
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // validation of fields
                if (name.equals("")){
                    showError(getResources().getString(R.string.error),getResources().getString(R.string.validname));
                    return;
                }


                    if (email.equals("")){
                        showError(getResources().getString(R.string.error),getResources().getString(R.string.vaildemail));
                        return;
                    }

                if (!isValidEmail(email)) {
                    showError(getResources().getString(R.string.error),getResources().getString(R.string.vaildemail));
                    return;
                }



                if (password.equals("")){
                    showError(getResources().getString(R.string.error),getResources().getString(R.string.validpass));
                    return;
                }

                saveInSession(name,email,password);
                openAnotherActivity(MainActivity.class);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAnotherActivity(LoginActivity.class);
            }
        });

    }
    // method to open desire activity
    private void openAnotherActivity(Class myClass){
        Intent intent = new Intent(RegisterActivity.this, myClass);
        startActivity(intent);
        finish();
    }

    // saving data in shared preferences
    private void saveInSession(String name, String email, String password){
        session.storeStringData(Constants.NAME,name);
        session.storeStringData(Constants.EMAIL,email);
        session.storeStringData(Constants.PASSWORD,password);
        session.storeBoolenData(Constants.ISLOGIN,true);
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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}
