package com.example.navigation_screen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for handling shared preferences in the application.
 * Provides methods to save and retrieve user-specific preferences such as the user ID.
 */
public class PreferencesUtil {

    // Name of the shared preferences file.
    private static final String PREFS_NAME = "MyAppPreferences";

    // Key used to store the user ID.
    private static final String KEY_USER_ID = "userid";

    /**
     * Saves the user ID in shared preferences.
     * This method stores the user ID so it can be retrieved across
     * different parts of the application.
     * @param context The context used to access the shared preferences.
     * @param userId The user ID to be saved.
     */
    public static void saveUserId(Context context, int userId) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    /**
     * Retrieves the saved user ID from shared preferences.
     * Returns a default value (0) if the user ID is not found.
     *
     * @param context The context used to access the shared preferences.
     * @return The saved user ID or a default value (0) if not found.
     */
    public static int getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Return the saved user id or a default value (0) if not found
        return preferences.getInt(KEY_USER_ID, 0);
    }
}

