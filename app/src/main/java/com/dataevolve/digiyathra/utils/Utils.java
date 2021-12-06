package com.dataevolve.digiyathra.utils;

import android.util.Base64;

public class Utils {
    public static String encodeToString(byte[] input) {

        return Base64.encodeToString(input, Base64.DEFAULT | Base64.NO_WRAP);
    }
}
