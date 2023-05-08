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

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Pizza> mList;
    public ListAdapter(List<Pizza> list, Context context){
        super();
        mList = list;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Pizza> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        mList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pizzalist,parent,false);
        ViewHolder viewHolder1 = new ViewHolder(v);
        return viewHolder1;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Pizza pizza = mList.get(position);

        Currency cur = Currency.getInstance("USD");
        // Get and print the symbol of the currency
        String symbol = cur.getSymbol();
        ((ViewHolder) holder).name.setText( String.valueOf(pizza.getName()));
        ((ViewHolder) holder).price.setText(symbol + String.valueOf(pizza.getPrice()));
        ((ViewHolder) holder).time.setText( String.valueOf(pizza.getPrepTime()));
        ((ViewHolder) holder).calories.setText( String.valueOf(pizza.getCalories()));
        ((ViewHolder) holder).size.setText("SIZE " + String.valueOf(pizza.getSize()));
        ((ViewHolder) holder).imageView.setImageResource(pizza.getImage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView size,name,price,calories,time;
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            size = (TextView) itemView.findViewById(R.id.tvSize);
            name = (TextView) itemView.findViewById(R.id.tvName);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            calories = (TextView) itemView.findViewById(R.id.tvCalories);
            time = (TextView) itemView.findViewById(R.id.tvTime);
            imageView = itemView.findViewById(R.id.imgPizza);
        }
    }
}