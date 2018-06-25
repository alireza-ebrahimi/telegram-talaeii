package com.persianswitch.sdk.base.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import java.util.Locale;

public class ResourceUtils {
    public static synchronized String getStringForLang(Context context, String lang, int resId) {
        String string;
        synchronized (ResourceUtils.class) {
            Configuration conf = context.getApplicationContext().getResources().getConfiguration();
            Locale currentLocale = conf.locale;
            conf.locale = new Locale(lang);
            DisplayMetrics metrics = new DisplayMetrics();
            string = new Resources(context.getApplicationContext().getAssets(), metrics, conf).getString(resId);
            conf.locale = currentLocale;
            Resources resources = new Resources(context.getApplicationContext().getAssets(), metrics, conf);
        }
        return string;
    }
}
