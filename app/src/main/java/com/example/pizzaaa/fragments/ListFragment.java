package com.example.pizzaaa.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaaa.ConfirmOrderActivity;
import com.example.pizzaaa.R;
import com.example.pizzaaa.dataClasses.Pizza;
import com.example.pizzaaa.database.DBHelper;
import com.example.pizzaaa.utils.MyRecycleItemClick;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    RecyclerView rcView;
    EditText etSearch;
    View v;
    List<Pizza> mList = new ArrayList<>();
    DBHelper dbh;
    ListAdapter mAdapter;
    Pizza pizzaData;
    public ListFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frament_list, container, false);

        rcView = v.findViewById(R.id.rv);
        etSearch = v.findViewById(R.id.etSearch);

        getActivity().setTitle("Menu");

        dbh = new DBHelper(getActivity());
        Cursor cursor1 = dbh.ListItems();
        if (cursor1.getCount() == 0) {
            Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
            return v;
        } else {
            cursor1.moveToFirst();
            do {

                Pizza pizza = new Pizza();
                pizza.setId(cursor1.getInt(0));
                pizza.setName(cursor1.getString(1));
                pizza.setQuantity(cursor1.getInt(2));
                pizza.setPrice(cursor1.getInt(3));
                pizza.setCalories(cursor1.getString(4));
                pizza.setPrepTime(cursor1.getString(5));
                pizza.setSize(cursor1.getString(6));
                pizza.setImage(cursor1.getInt(7));
                mList.add(pizza);

            } while (cursor1.moveToNext());
            cursor1.close();
            dbh.close();
            bindAdapter();


            rcView.addOnItemTouchListener(new MyRecycleItemClick(getActivity(), new MyRecycleItemClick.OnItemClickListener(){
                @Override
                public void onItemClick(View view, int position) {

                    pizzaData = mList.get(position);

                    Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                    intent.putExtra("PIZZADATA",pizzaData);
                    startActivity(intent);

                }
            }));




            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.length() > 2 ){
                        //Cursor cursor1 = dbh.getById(s.toString());
                       // Toast.makeText(getActivity(), "vdvddvd- " + cursor1.getCount() + s.toString(), Toast.LENGTH_SHORT).show();
                        filter(s.toString());
                    } else {
                        bindAdapter();
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });







            return v;
        }

    }


    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Pizza> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Pizza item : mList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            mAdapter.filterList(filteredlist);
        }
    }



    private void bindAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(mList,getContext());
        rcView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
