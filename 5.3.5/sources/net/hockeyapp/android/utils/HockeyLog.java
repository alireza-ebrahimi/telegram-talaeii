package net.hockeyapp.android.utils;

import android.util.Log;

public class HockeyLog {
    public static final String HOCKEY_TAG = "HockeyApp";
    private static int sLogLevel = 6;

    public static int getLogLevel() {
        return sLogLevel;
    }

    public static void setLogLevel(int hockeyLogLevel) {
        sLogLevel = hockeyLogLevel;
    }

    public static void verbose(String message) {
        verbose(null, message);
    }

    public static void verbose(String tag, String message) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 2) {
            Log.v(tag, message);
        }
    }

    public static void verbose(String message, Throwable throwable) {
        verbose(null, message, throwable);
    }

    public static void verbose(String tag, String message, Throwable throwable) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 2) {
            Log.v(tag, message, throwable);
        }
    }

    public static void debug(String message) {
        debug(null, message);
    }

    public static void debug(String tag, String message) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 3) {
            Log.d(tag, message);
        }
    }

    public static void debug(String message, Throwable throwable) {
        debug(null, message, throwable);
    }

    public static void debug(String tag, String message, Throwable throwable) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 3) {
            Log.d(tag, message, throwable);
        }
    }

    public static void info(String message) {
        info(null, message);
    }

    public static void info(String tag, String message) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 4) {
            Log.i(tag, message);
        }
    }

    public static void info(String message, Throwable throwable) {
        info(message, throwable);
    }

    public static void info(String tag, String message, Throwable throwable) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 4) {
            Log.i(tag, message, throwable);
        }
    }

    public static void warn(String message) {
        warn(null, message);
    }

    public static void warn(String tag, String message) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 5) {
            Log.w(tag, message);
        }
    }

    public static void warn(String message, Throwable throwable) {
        warn(null, message, throwable);
    }

    public static void warn(String tag, String message, Throwable throwable) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 5) {
            Log.w(tag, message, throwable);
        }
    }

    public static void error(String message) {
        error(null, message);
    }

    public static void error(String tag, String message) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 6) {
            Log.e(tag, message);
        }
    }

    public static void error(String message, Throwable throwable) {
        error(null, message, throwable);
    }

    public static void error(String tag, String message, Throwable throwable) {
        tag = sanitizeTag(tag);
        if (sLogLevel <= 6) {
            Log.e(tag, message, throwable);
        }
    }

    static String sanitizeTag(String tag) {
        if (tag == null || tag.length() == 0 || tag.length() > 23) {
            return "HockeyApp";
        }
        return tag;
    }
}
