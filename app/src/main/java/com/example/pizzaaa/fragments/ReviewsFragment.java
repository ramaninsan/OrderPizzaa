package com.example.pizzaaa.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaaa.R;
import com.example.pizzaaa.dataClasses.Pizza;
import com.example.pizzaaa.dataClasses.Reviews;
import com.example.pizzaaa.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends Fragment {


    RecyclerView rcView;
    View v;
    List<Reviews> mList = new ArrayList<>();
    DBHelper dbh;
    ReviewsAdapter mAdapter;
    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_reviews, container, false);


        rcView = v.findViewById(R.id.rvReviews);
        dbh = new DBHelper(getActivity());
        Cursor cursor1 = dbh.ListReviews();

        if (cursor1.getCount() == 0) {
            Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
            return v;
        } else {
            cursor1.moveToFirst();
            do {

                Reviews reviews = new Reviews();
                reviews.setPizzaName(cursor1.getString(0));
                reviews.setUsernames(cursor1.getString(1));
                reviews.setUserPic(cursor1.getInt(2));
                reviews.setRating(cursor1.getString(3));
                reviews.setComments(cursor1.getString(4));
                reviews.setLikes(cursor1.getString(5));
                mList.add(reviews);

            } while (cursor1.moveToNext());
            cursor1.close();
            dbh.close();


            bindAdapter();
            return v;
        }
    }

        private void bindAdapter() {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            rcView.setLayoutManager(layoutManager);
            mAdapter = new ReviewsAdapter(mList, getContext());
            rcView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

