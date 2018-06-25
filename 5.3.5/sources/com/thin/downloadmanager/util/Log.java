package com.thin.downloadmanager.util;

public final class Log {
    private static boolean sEnabled = false;
    private static String sTag = "ThinDownloadManager";

    private Log() {
    }

    public static boolean isEnabled() {
        return sEnabled;
    }

    public static void setEnabled(boolean enabled) {
        sEnabled = enabled;
    }

    public static boolean isLoggingEnabled() {
        return sEnabled;
    }

    /* renamed from: v */
    public static int m53v(String msg) {
        if (sEnabled) {
            return android.util.Log.v(sTag, msg);
        }
        return 0;
    }

    /* renamed from: v */
    public static int m54v(String tag, String msg) {
        if (sEnabled) {
            return android.util.Log.v(tag, msg);
        }
        return 0;
    }

    /* renamed from: v */
    public static int m56v(String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.v(sTag, msg, tr);
        }
        return 0;
    }

    /* renamed from: v */
    public static int m55v(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.v(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m40d(String msg) {
        if (sEnabled) {
            return android.util.Log.d(sTag, msg);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m41d(String tag, String msg) {
        if (sEnabled) {
            return android.util.Log.d(tag, msg);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m43d(String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.d(sTag, msg, tr);
        }
        return 0;
    }

    /* renamed from: d */
    public static int m42d(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.d(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m48i(String msg) {
        if (sEnabled) {
            return android.util.Log.i(sTag, msg);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m49i(String tag, String msg) {
        if (sEnabled) {
            return android.util.Log.i(tag, msg);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m51i(String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.i(sTag, msg, tr);
        }
        return 0;
    }

    /* renamed from: i */
    public static int m50i(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.i(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m57w(String msg) {
        if (sEnabled) {
            return android.util.Log.w(sTag, msg);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m58w(String tag, String msg) {
        if (sEnabled) {
            return android.util.Log.w(tag, msg);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m60w(String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.w(sTag, msg, tr);
        }
        return 0;
    }

    /* renamed from: w */
    public static int m59w(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.w(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m44e(String msg) {
        if (sEnabled) {
            return android.util.Log.e(sTag, msg);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m45e(String tag, String msg) {
        if (sEnabled) {
            return android.util.Log.e(tag, msg);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m47e(String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.e(sTag, msg, tr);
        }
        return 0;
    }

    /* renamed from: e */
    public static int m46e(String tag, String msg, Throwable tr) {
        if (sEnabled) {
            return android.util.Log.e(tag, msg, tr);
        }
        return 0;
    }

    /* renamed from: t */
    public static int m52t(String msg, Object... args) {
        if (sEnabled) {
            return android.util.Log.v("test", String.format(msg, args));
        }
        return 0;
    }
}
