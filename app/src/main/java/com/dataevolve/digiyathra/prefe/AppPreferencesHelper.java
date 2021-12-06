package com.dataevolve.digiyathra.prefe;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String TOKEN = "TOKEN";



    private final SharedPreferences mPrefs;


    public AppPreferencesHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public String getToken() {
        return mPrefs.getString(TOKEN, "");
    }

    @Override
    public void setToken(String token) {
        mPrefs.edit().putString(TOKEN, token).apply();

    }

    @Override
    public void clearAll() {
        mPrefs.edit().remove(TOKEN).apply();
    }




}
