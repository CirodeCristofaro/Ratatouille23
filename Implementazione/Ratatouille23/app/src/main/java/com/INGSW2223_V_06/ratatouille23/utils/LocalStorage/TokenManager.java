package com.INGSW2223_V_06.ratatouille23.utils.LocalStorage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Objects;

public class TokenManager {
    private static TokenManager instance;
    private static SharedPreferences preferences;
    private static final String TAG = "TokenManager";

    private TokenManager(Context context) {
        preferences = context.getSharedPreferences("token", MODE_PRIVATE);
    }

    public static void deleteToken() {
        if (preferences != null) {
            preferences.edit().remove("token").apply();
            Log.i(TAG, Objects.isNull(preferences.getString("token", "")) ?
                    "token eliminato " : preferences.getString("token", ""));
        }
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context);
        }
        return instance;
    }

    public String getToken() {
        return preferences.getString("token", "");
    }

    public void setToken(String token) {
        preferences.edit().putString("token", token).apply();
    }
}