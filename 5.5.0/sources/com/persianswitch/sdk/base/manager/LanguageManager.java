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
    /* renamed from: a */
    private static LanguageManager f7076a;
    /* renamed from: b */
    private final Context f7077b;

    private LanguageManager(Context context) {
        if (f7076a != null) {
            throw new InstantiationError();
        }
        this.f7077b = context;
    }

    /* renamed from: a */
    public static Context m10667a(Context context, Locale locale) {
        Resources resources = context.getResources();
        if (StringUtils.m10805a(m10674c(context).getLanguage(), locale.getLanguage())) {
            return context;
        }
        Configuration a = m10668a(resources.getConfiguration(), locale);
        if (m10675d()) {
            return context.createConfigurationContext(a);
        }
        resources.updateConfiguration(a, resources.getDisplayMetrics());
        return context;
    }

    /* renamed from: a */
    private static Configuration m10668a(Configuration configuration, Locale locale) {
        Configuration configuration2 = new Configuration(configuration);
        if (m10675d()) {
            configuration2.setLocale(locale);
        } else {
            configuration2.locale = locale;
        }
        return configuration2;
    }

    /* renamed from: a */
    public static LanguageManager m10669a(Context context) {
        if (f7076a == null) {
            f7076a = new LanguageManager(context);
        }
        return f7076a;
    }

    /* renamed from: a */
    public static String m10670a() {
        return "fa";
    }

    /* renamed from: a */
    public static void m10671a(Context context, ContextThemeWrapper contextThemeWrapper) {
        Locale c = m10674c(context);
        Locale locale = new Locale(m10669a(context).m10678c());
        if (!StringUtils.m10805a(c.getLanguage(), locale.getLanguage())) {
            m10672a(contextThemeWrapper, m10668a(context.getResources().getConfiguration(), locale));
        }
        m10673b(context.getApplicationContext());
    }

    /* renamed from: a */
    private static void m10672a(ContextThemeWrapper contextThemeWrapper, Configuration configuration) {
        try {
            if (m10675d()) {
                contextThemeWrapper.applyOverrideConfiguration(configuration);
                return;
            }
            Resources resources = contextThemeWrapper.getResources();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        } catch (Throwable th) {
        }
    }

    /* renamed from: b */
    public static Context m10673b(Context context) {
        return m10667a(context, new Locale(m10669a(context).m10678c()));
    }

    /* renamed from: c */
    private static Locale m10674c(Context context) {
        return VERSION.SDK_INT >= 24 ? context.getResources().getConfiguration().getLocales().get(0) : context.getResources().getConfiguration().locale;
    }

    /* renamed from: d */
    private static boolean m10675d() {
        return VERSION.SDK_INT >= 23;
    }

    /* renamed from: a */
    public void m10676a(String str) {
        if ("fa".equals(str) || "en".equals(str)) {
            BaseSetting.m10467e(this.f7077b, str);
        }
    }

    /* renamed from: b */
    public boolean m10677b() {
        return "fa".equals(m10678c());
    }

    /* renamed from: c */
    public String m10678c() {
        return BaseSetting.m10472i(this.f7077b);
    }
}
