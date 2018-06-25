package com.android.volley;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VolleyLog {
    public static boolean DEBUG = Log.isLoggable(TAG, 2);
    public static String TAG = "Volley";

    static class MarkerLog {
        public static final boolean ENABLED = VolleyLog.DEBUG;
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0;
        private boolean mFinished = false;
        private final List<Marker> mMarkers = new ArrayList();

        private static class Marker {
            public final String name;
            public final long thread;
            public final long time;

            public Marker(String name, long thread, long time) {
                this.name = name;
                this.thread = thread;
                this.time = time;
            }
        }

        MarkerLog() {
        }

        public synchronized void add(String name, long threadId) {
            if (this.mFinished) {
                throw new IllegalStateException("Marker added to finished log");
            }
            this.mMarkers.add(new Marker(name, threadId, SystemClock.elapsedRealtime()));
        }

        public synchronized void finish(String header) {
            this.mFinished = true;
            if (getTotalDuration() > 0) {
                long prevTime = ((Marker) this.mMarkers.get(0)).time;
                VolleyLog.m15d("(%-4d ms) %s", Long.valueOf(duration), header);
                for (Marker marker : this.mMarkers) {
                    VolleyLog.m15d("(+%-4d) [%2d] %s", Long.valueOf(marker.time - prevTime), Long.valueOf(marker.thread), marker.name);
                    prevTime = marker.time;
                }
            }
        }

        protected void finalize() throws Throwable {
            if (!this.mFinished) {
                finish("Request on the loose");
                VolleyLog.m16e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        private long getTotalDuration() {
            if (this.mMarkers.size() == 0) {
                return 0;
            }
            return ((Marker) this.mMarkers.get(this.mMarkers.size() - 1)).time - ((Marker) this.mMarkers.get(0)).time;
        }
    }

    public static void setTag(String tag) {
        m15d("Changing log tag to %s", tag);
        TAG = tag;
        DEBUG = Log.isLoggable(TAG, 2);
    }

    /* renamed from: v */
    public static void m18v(String format, Object... args) {
        if (DEBUG) {
            Log.v(TAG, buildMessage(format, args));
        }
    }

    /* renamed from: d */
    public static void m15d(String format, Object... args) {
        Log.d(TAG, buildMessage(format, args));
    }

    /* renamed from: e */
    public static void m16e(String format, Object... args) {
        Log.e(TAG, buildMessage(format, args));
    }

    /* renamed from: e */
    public static void m17e(Throwable tr, String format, Object... args) {
        Log.e(TAG, buildMessage(format, args), tr);
    }

    public static void wtf(String format, Object... args) {
        Log.wtf(TAG, buildMessage(format, args));
    }

    public static void wtf(Throwable tr, String format, Object... args) {
        Log.wtf(TAG, buildMessage(format, args), tr);
    }

    private static String buildMessage(String format, Object... args) {
        String msg = args == null ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";
        for (int i = 2; i < trace.length; i++) {
            if (!trace[i].getClass().equals(VolleyLog.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf(46) + 1);
                String valueOf = String.valueOf(String.valueOf(callingClass.substring(callingClass.lastIndexOf(36) + 1)));
                String valueOf2 = String.valueOf(String.valueOf(trace[i].getMethodName()));
                caller = new StringBuilder((valueOf.length() + 1) + valueOf2.length()).append(valueOf).append(".").append(valueOf2).toString();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), caller, msg});
    }
}
