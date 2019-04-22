package com.appscrip.trivia.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PrefferenceManager {
    public static void setUserName(Context context, String name) {
        saveData(Constant.Prefference.USER_NAME, name, context);
    }

    public static String getUserName(Context context) {
        return getData(Constant.Prefference.USER_NAME, String.class, "", context);
    }

    public static Integer getGameNumber(Context context) {
        return getData(Constant.Prefference.GAME_NUMBER, Integer.class, 1, context);
    }

    public static void setGameNumber(Context context, int gameNumber) {
        saveData(Constant.Prefference.GAME_NUMBER, gameNumber, context);
    }

    private static void saveData(String key, Object value, Context context) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set) value);
        }
        editor.apply();
    }

    private static <T> T getData(String key, Class<T> type, T defaultValue, Context context) {
        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        if (type.isAssignableFrom(Integer.class)) {
            return (T) Integer.valueOf((sharedPreferences.getInt(key, (Integer) defaultValue)));
        }
        if (type.isAssignableFrom(String.class)) {
            return (T) (sharedPreferences.getString(key, (String) defaultValue));
        }
        if (type.isAssignableFrom(Long.class)) {
            return (T) Long.valueOf((sharedPreferences.getLong(key, (Long) defaultValue)));
        }
        if (type.isAssignableFrom(Boolean.class)) {
            return (T) Boolean.valueOf((sharedPreferences.getBoolean(key, (Boolean) defaultValue)));
        }
        if (type.isAssignableFrom(Set.class)) {
            return (T) (sharedPreferences.getStringSet(key, (Set) defaultValue));
        }
        return null;
    }
}
