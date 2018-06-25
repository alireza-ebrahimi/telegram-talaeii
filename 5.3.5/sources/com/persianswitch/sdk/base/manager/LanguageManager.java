package com.persianswitch.sdk.base.manager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.view.ContextThemeWrapper;
import com.persianswitch.sdk.base.BaseSetting;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import java.util.Locale;

public final class LanguageManager {
    public static final String ENGLISH = "en";
    public static final String PERSIAN = "fa";
    private static LanguageManager instance;
    private final Context mContext;

    private LanguageManager(Context context) {
        if (instance != null) {
            throw new InstantiationError();
        }
        this.mContext = context;
    }

    public static LanguageManager getInstance(Context context) {
        if (instance == null) {
            instance = new LanguageManager(context);
        }
        return instance;
    }

    public static String getDefaultLanguage() {
        return PERSIAN;
    }

    public boolean isPersian() {
        return PERSIAN.equals(getCurrentLanguage());
    }

    public String getCurrentLanguage() {
        return BaseSetting.getLanguage(this.mContext);
    }

    public void setCurrentLanguage(String languageTag) {
        if (PERSIAN.equals(languageTag) || ENGLISH.equals(languageTag)) {
            BaseSetting.setLanguage(this.mContext, languageTag);
        }
    }

    public void reloadResourceForLanguage() {
        reloadResourceForLanguage(getCurrentLanguage());
    }

    private static Locale getLocale(Context context) {
        if (VERSION.SDK_INT >= 24) {
            return context.getResources().getConfiguration().getLocales().get(0);
        }
        return context.getResources().getConfiguration().locale;
    }

    public static void applyCurrentLocale(Context baseCtx, ContextThemeWrapper contextThemeWrapper) {
        Locale oldLocale = getLocale(baseCtx);
        Locale currentLocale = new Locale(getInstance(baseCtx).getCurrentLanguage());
        if (!StringUtils.isEquals(oldLocale.getLanguage(), currentLocale.getLanguage())) {
            applyOverrideConfiguration(contextThemeWrapper, localizeConfiguration(baseCtx.getResources().getConfiguration(), currentLocale));
        }
        localizeContext(baseCtx.getApplicationContext());
    }

    private static boolean useNewLocalization() {
        return VERSION.SDK_INT >= 23;
    }

    private static void applyOverrideConfiguration(ContextThemeWrapper context, Configuration config) {
        try {
            if (useNewLocalization()) {
                context.applyOverrideConfiguration(config);
                return;
            }
            Resources resources = context.getResources();
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        } catch (Throwable th) {
        }
    }

    public static Context localizeContext(Context context) {
        return localizeContext(context, new Locale(getInstance(context).getCurrentLanguage()));
    }

    public static Context localizeContext(Context context, Locale newLocale) {
        Resources resources = context.getResources();
        if (StringUtils.isEquals(getLocale(context).getLanguage(), newLocale.getLanguage())) {
            return context;
        }
        Configuration localizeConfiguration = localizeConfiguration(resources.getConfiguration(), newLocale);
        if (useNewLocalization()) {
            return context.createConfigurationContext(localizeConfiguration);
        }
        resources.updateConfiguration(localizeConfiguration, resources.getDisplayMetrics());
        return context;
    }

    private static Configuration localizeConfiguration(Configuration configuration, Locale locale) {
        Configuration overrideConfiguration = new Configuration(configuration);
        if (useNewLocalization()) {
            overrideConfiguration.setLocale(locale);
        } else {
            overrideConfiguration.locale = locale;
        }
        return overrideConfiguration;
    }

    public void reloadResourceForLanguage(String languageTag) {
        if (PERSIAN.equals(languageTag) || ENGLISH.equals(languageTag)) {
            Resources res = this.mContext.getResources();
            Configuration conf = res.getConfiguration();
            if (!conf.locale.getLanguage().equals(languageTag)) {
                conf.locale = new Locale(languageTag);
                res.updateConfiguration(conf, res.getDisplayMetrics());
            }
        }
    }
}
