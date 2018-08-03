package com.hikki.sergey_natalenko.testapp.utils;

import android.util.Log;

public class Hashing {
    public static String toHash(String s){

        long hash = 7;
        for (int i = 0; i < s.length(); i++) {
            hash = hash * 17 + s.charAt(i)+s.hashCode();
        }
        Log.i("Hashing",hash+"");
        return hash+"";
    }
}
