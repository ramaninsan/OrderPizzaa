package com.example.pizzaaa.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cts on 27/5/16.
 */
public class AppSession {
    private  SharedPreferences preferences;
    private static AppSession session;

    public AppSession(Context context) {
         preferences = context.getSharedPreferences("appData", Context.MODE_PRIVATE);
         }

    public  static  AppSession getInstance(Context context){

        if(session == null)
        {
            session = new AppSession(context);
        }
        return  session;
    }

    public String getStringData(String key) {
        return preferences.getString(key, "NA");

    }

    public void storeStringData(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public void storeBoolenData(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolenData(String key) {
        return preferences.getBoolean(key, false);

    }

    public void storeInt(String key,int value)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public int getIntdata(String key )
    {
        return preferences.getInt(key, 0);

    }

    public void clearData(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }
}
