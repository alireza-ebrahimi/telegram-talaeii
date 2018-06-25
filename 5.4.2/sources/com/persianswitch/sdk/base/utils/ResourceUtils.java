package com.persianswitch.sdk.base.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import java.util.Locale;

public class ResourceUtils {
    /* renamed from: a */
    public static synchronized String m10763a(Context context, String str, int i) {
        String string;
        synchronized (ResourceUtils.class) {
            Configuration configuration = context.getApplicationContext().getResources().getConfiguration();
            Locale locale = configuration.locale;
            configuration.locale = new Locale(str);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            string = new Resources(context.getApplicationContext().getAssets(), displayMetrics, configuration).getString(i);
            configuration.locale = locale;
            Resources resources = new Resources(context.getApplicationContext().getAssets(), displayMetrics, configuration);
        }
        return string;
    }
}
