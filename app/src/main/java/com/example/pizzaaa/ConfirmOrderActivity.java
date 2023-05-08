package com.example.pizzaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pizzaaa.dataClasses.Pizza;
import com.example.pizzaaa.fragments.ListFragment;
import com.example.pizzaaa.fragments.OrderPizzaFragment;
import com.example.pizzaaa.fragments.ReviewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ConfirmOrderActivity extends AppCompatActivity {

    Pizza data;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        init();



        if(savedInstanceState == null) {

            Intent intent = getIntent();
            data = (Pizza) intent.getSerializableExtra("PIZZADATA");

            Bundle mBundle = new Bundle();
            mBundle.putSerializable("PIZZADATA",data);
            Fragment fragment = new OrderPizzaFragment();
            fragment.setArguments(mBundle);
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.frame,fragment).commit();
        }

    }

    private void init(){
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        Intent intent = getIntent();
        data = (Pizza) intent.getSerializableExtra("PIZZADATA");

        Bundle mBundle = new Bundle();
        mBundle.putSerializable("PIZZADATA",data);
        Fragment fragment = new OrderPizzaFragment();
        fragment.setArguments(mBundle);

        setBottomNavigationView();
    }

    // opening fragments on click of bottom nav view item click listeners
    private void setBottomNavigationView(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_bottom_order:
                        fragment = new OrderPizzaFragment();

                        break;

                    case R.id.nav_bottom_reviews:
                        fragment = new ReviewsFragment();
                        break;
                }
                if (fragment!=null){

                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("PIZZADATA",data);
                    fragment.setArguments(mBundle);
                   // mFragmentTransaction.add(R.id.frameLayout, mFragment).commit()


                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame,fragment);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });
    }
}
