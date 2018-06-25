package com.googlecode.mp4parser.util;

import android.util.Log;

public class AndroidLogger extends Logger {
    private static final String TAG = "isoparser";
    String name;

    public AndroidLogger(String name) {
        this.name = name;
    }

    public void logDebug(String message) {
        Log.d(TAG, this.name + ":" + message);
    }

    public void logWarn(String message) {
        Log.w(TAG, this.name + ":" + message);
    }

    public void logError(String message) {
        Log.e(TAG, this.name + ":" + message);
    }
}
