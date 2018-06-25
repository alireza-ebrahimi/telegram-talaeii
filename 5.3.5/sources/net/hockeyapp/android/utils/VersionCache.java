package net.hockeyapp.android.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import net.hockeyapp.android.UpdateFragment;

public class VersionCache {
    private static String PREF_VERSION_INFO_KEY = UpdateFragment.FRAGMENT_VERSION_INFO;

    public static void setVersionInfo(Context context, String json) {
        if (context != null) {
            Editor editor = context.getSharedPreferences("HockeyApp", 0).edit();
            editor.putString(PREF_VERSION_INFO_KEY, json);
            editor.apply();
        }
    }

    public static String getVersionInfo(Context context) {
        if (context != null) {
            return context.getSharedPreferences("HockeyApp", 0).getString(PREF_VERSION_INFO_KEY, "[]");
        }
        return "[]";
    }
}
