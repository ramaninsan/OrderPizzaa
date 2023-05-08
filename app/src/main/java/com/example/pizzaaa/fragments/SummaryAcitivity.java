package com.example.pizzaaa.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzaaa.R;
import com.example.pizzaaa.dataClasses.Orders;

public class SummaryAcitivity extends AppCompatActivity {

    Orders orders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();
        orders = (Orders) intent.getSerializableExtra("ORDERSDATA");

        Toast.makeText(this, "fff "+ orders.getPrice(), Toast.LENGTH_SHORT).show();

    }
}
