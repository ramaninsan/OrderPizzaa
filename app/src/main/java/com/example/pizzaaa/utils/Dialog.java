package com.example.pizzaaa.utils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class Dialog extends DialogFragment {

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete this order history?")
                .setTitle("Message")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "OK pressed", Toast.LENGTH_SHORT).show();

                        // SATURDAY(ANY SHIFT),
                        // SUNDAY(ANY SHIFT)
                        // MONDAY (ANY SHIFT),

                        // WEDNESDAY(AFTERNOON),

                        // FRIDAY(MORNING SHIFT),


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel pressed", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }
}
