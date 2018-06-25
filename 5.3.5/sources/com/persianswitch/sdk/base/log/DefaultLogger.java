package com.persianswitch.sdk.base.log;

import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

class DefaultLogger implements ILogger {
    DefaultLogger() {
    }

    public void log(int priority, String tag, String message, Throwable t, Object... args) {
        if (message != null && message.length() == 0) {
            message = null;
        }
        if (message != null) {
            if (args.length > 0) {
                message = String.format(Locale.US, message, args);
            }
            if (t != null) {
                message = message + LogCollector.LINE_SEPARATOR + getStackTraceString(t);
            }
        } else if (t != null) {
            message = getStackTraceString(t);
        } else {
            return;
        }
        switch (priority) {
            case 2:
                Log.v(tag, message);
                return;
            case 3:
                Log.d(tag, message);
                return;
            case 4:
                Log.i(tag, message);
                return;
            case 5:
                Log.w(tag, message);
                return;
            case 6:
                Log.e(tag, message);
                return;
            case 7:
                Log.e(tag, message);
                return;
            default:
                return;
        }
    }

    public String collect() {
        return "";
    }

    public void clear() {
    }

    String getStackTraceString(Throwable t) {
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        pw.flush();
        pw.close();
        return sw.toString();
    }
}
