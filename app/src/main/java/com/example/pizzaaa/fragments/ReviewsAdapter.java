package com.example.pizzaaa.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaaa.R;
import com.example.pizzaaa.dataClasses.Pizza;
import com.example.pizzaaa.dataClasses.Reviews;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Reviews> mList;
    public ReviewsAdapter(List<Reviews> list, Context context){
        super();
        mList = list;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review,parent,false);
        ReviewsAdapter.ViewHolder viewHolder1 = new ReviewsAdapter.ViewHolder(v);
        return viewHolder1;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Reviews reviews = mList.get(position);
        ((ReviewsAdapter.ViewHolder) holder).pizzaName.setText( String.valueOf(reviews.getPizzaName()));
        ((ReviewsAdapter.ViewHolder) holder).userName.setText("By- " + reviews.getUsernames());
        ((ReviewsAdapter.ViewHolder) holder).rating.setText( String.valueOf(reviews.getRating()));
        ((ReviewsAdapter.ViewHolder) holder).comment.setText( String.valueOf(reviews.getComments()));
        ((ReviewsAdapter.ViewHolder) holder).likes.setText(reviews.getLikes());
        ((ReviewsAdapter.ViewHolder) holder).imageView.setImageResource(reviews.getUserPic());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView pizzaName,userName,rating,comment,likes;
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            pizzaName = (TextView) itemView.findViewById(R.id.tvPizzaName);
            userName = (TextView) itemView.findViewById(R.id.tvByUser);
            rating = (TextView) itemView.findViewById(R.id.tvRating);
            comment = (TextView) itemView.findViewById(R.id.tvRevDesc);
            likes = (TextView) itemView.findViewById(R.id.tvLikes);
            imageView = itemView.findViewById(R.id.imgUser);
        }
    }
}