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
import com.example.pizzaaa.dataClasses.Orders;
import com.example.pizzaaa.dataClasses.Pizza;

import java.util.ArrayList;
import java.util.List;

public class ListOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Orders> mList;
    public ListOrdersAdapter(List<Orders> list, Context context){
        super();
        mList = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_orders,parent,false);
        ListOrdersAdapter.ViewHolder viewHolder1 = new ListOrdersAdapter.ViewHolder(v);
        return viewHolder1;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Orders pizza = mList.get(position);
        ((ListOrdersAdapter.ViewHolder) holder).name.setText( String.valueOf(pizza.getName()));
        ((ListOrdersAdapter.ViewHolder) holder).price.setText("CAD \n" + String.valueOf(pizza.getPrice()));
        ((ViewHolder) holder).size.setText(pizza.getQuantity()+"\n"+pizza.getSize());
        ((ViewHolder) holder).layer.setText( String.valueOf(pizza.getStyle()));
        ((ViewHolder) holder).toppings.setText(pizza.getToppings());
        ((ViewHolder) holder).status.setText("to be delievered..");
        ((ListOrdersAdapter.ViewHolder) holder).imageView.setImageResource(pizza.getImage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView size,name,price,toppings,layer,status;
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            size = (TextView) itemView.findViewById(R.id.tvQuantity);
            name = (TextView) itemView.findViewById(R.id.tvName);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            toppings = (TextView) itemView.findViewById(R.id.tvToppings);
            layer = (TextView) itemView.findViewById(R.id.tvLayerType);
            status = (TextView) itemView.findViewById(R.id.tvStatus);
            imageView = itemView.findViewById(R.id.imgPizza);
        }
    }
}
