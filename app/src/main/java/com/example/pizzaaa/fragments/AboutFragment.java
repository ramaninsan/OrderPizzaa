package com.example.pizzaaa.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pizzaaa.R;

public class AboutFragment extends Fragment {

    View v;
    TextView tvDesc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_about, container, false);
      tvDesc = v.findViewById(R.id.tvDesc);

        getActivity().setTitle(getActivity().getResources().getString(R.string.aboutus));
        tvDesc.setText(getActivity().getResources().getString(R.string.desc));
        return v;
    }
}
