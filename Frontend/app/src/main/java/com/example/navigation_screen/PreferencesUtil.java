package com.example.navigation_screen;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {

    private static final String PREFS_NAME = "MyAppPreferences";
    private static final String KEY_USER_ID = "userid";

    // Save user id
    public static void saveUserId(Context context, int userId) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    // Get user id
    public static int getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Return the saved user id or a default value (0) if not found
        return preferences.getInt(KEY_USER_ID, 0);
    }
}

