package com.example.secondtask;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RadioButton;

public class PrefsHelper {

    public PrefsHelper(Context context) {
        this.context = context;
    };

    public static final String SECOND_TASK_PREFS = "SETTINGS";
    final String KEY_RADIOBUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    final String SEARCH_SYSTEM = "SEARCH";
    Context context;

    public void savePrefs(int i, String s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SECOND_TASK_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_RADIOBUTTON_INDEX, i).putString(SEARCH_SYSTEM, s).apply();
    }

    public int loadButtonIndex() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SECOND_TASK_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_RADIOBUTTON_INDEX, 0);
    }

    public String loadSearcher() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SECOND_TASK_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SEARCH_SYSTEM, "https://www.google.ru/#q=");
    }
}
