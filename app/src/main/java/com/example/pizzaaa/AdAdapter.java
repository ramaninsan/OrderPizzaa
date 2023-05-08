package com.example.pizzaaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.demono.adapter.InfinitePagerAdapter;

import java.util.ArrayList;

public class AdAdapter extends InfinitePagerAdapter {

    private Integer[] data;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ImageView imageView;

    public AdAdapter(Context context,Integer[] data) {
        this.data = data;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup container) {
        View itemView = mLayoutInflater.inflate(R.layout.ad_layout, container, false);

        imageView = (ImageView) itemView.findViewById(R.id.imgAd);

        int imgId = data[position];
        imageView.setImageResource(imgId);

        container.addView(itemView);

        return itemView;
    }


}