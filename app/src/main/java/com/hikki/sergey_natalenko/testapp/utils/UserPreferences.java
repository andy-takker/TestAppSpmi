package com.hikki.sergey_natalenko.testapp.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.UUID;

public class UserPreferences {

    private static final String PREF_USER = "isLogged";
    private static final String PREF_ID = "uuidUser";

    public static boolean getStoredIsLogged(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_USER, false);
    }

    public static void setStoredIsLogged(Context context, boolean isLogged){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_USER, isLogged)
                .apply();
    }

    public static void setUserLoggedId(Context context, UUID uuid){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_ID, uuid.toString())
                .apply();
    }
    public static UUID getUserLoggedId(Context context){
        String s = getUserLoggedIdString(context);
        return UUID.fromString(s);
    }

    private static String getUserLoggedIdString(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_ID,"0f39ea4a-46ac-4c70-aaf0-3383e283961b");
    }
}
