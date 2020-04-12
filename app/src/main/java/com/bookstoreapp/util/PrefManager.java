package com.bookstoreapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String KEY_TOKEN = "keyToken";

    public static void storeAccessToken(Activity activity, String accessToken) {
        SharedPreferences.Editor editor = activity.getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, accessToken);
        editor.apply();
    }

    public static String retrieveAccessToken(Activity activity) {
        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        return preferences.getString(KEY_TOKEN, null);
    }
}
