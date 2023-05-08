package com.example.pizzaaa.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaaa.ConfirmOrderActivity;
import com.example.pizzaaa.R;
import com.example.pizzaaa.dataClasses.Orders;
import com.example.pizzaaa.dataClasses.Pizza;
import com.example.pizzaaa.database.DBHelper;
import com.example.pizzaaa.utils.Dialog;
import com.example.pizzaaa.utils.MyRecycleItemClick;

import java.util.ArrayList;
import java.util.List;

public class ListOrdersFragment extends Fragment {

    RecyclerView rcView;
    ImageView imgError;
    View v;
    List<Orders> mList = new ArrayList<>();
    DBHelper dbh;
    ListOrdersAdapter mAdapter;
    Orders ordersData;
    public ListOrdersFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_orders, container, false);
        getActivity().setTitle("Order Status");
        rcView = v.findViewById(R.id.rvListOrders);
        imgError = v.findViewById(R.id.imgError);

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();

        dbh = new DBHelper(getActivity());

        Cursor cursor1 = dbh.ListOrders();
        if (cursor1.getCount() == 0) {
            rcView.setVisibility(View.GONE);
            imgError.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "No Orders Found", Toast.LENGTH_SHORT).show();
        } else {
            cursor1.moveToFirst();
            do {

                Orders pizza = new Orders();
                pizza.setId(cursor1.getInt(0));
                pizza.setName(cursor1.getString(1));
                pizza.setQuantity(cursor1.getInt(2));
                pizza.setPrice(cursor1.getInt(3));
                pizza.setCalories(cursor1.getString(4));
                pizza.setPrepTime(cursor1.getString(5));
                pizza.setSize(cursor1.getString(6));
                pizza.setImage(cursor1.getInt(7));
                pizza.setStyle(cursor1.getString(8));
                pizza.setToppings(cursor1.getString(9));
                pizza.setOtherDesc(cursor1.getString(10));
                mList.add(pizza);

            } while (cursor1.moveToNext());
            cursor1.close();
            dbh.close();

            rcView.setVisibility(View.VISIBLE);
            imgError.setVisibility(View.GONE);

            bindAdapter();

            rcView.addOnItemTouchListener(new MyRecycleItemClick(getActivity(), new MyRecycleItemClick.OnItemClickListener(){
                @Override
                public void onItemClick(View view, int position) {

                    ordersData = mList.get(position);
                   // showDialog(ordersData.getId());
                }
            }));
        }

    }

    private void showDialog(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You want to delete this order history?")
                .setTitle("Alert")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dbh.DeleteOrders(id);
                        Toast.makeText(getActivity(), "OK pressed", Toast.LENGTH_SHORT).show();
                        onStart();
                       // mAdapter.notify();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create();

        AlertDialog alert = builder.create();
        alert.show();
    }


    private void bindAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcView.setLayoutManager(layoutManager);
        mAdapter = new ListOrdersAdapter(mList,getContext());
        rcView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
