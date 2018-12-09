package com.google.android.gms.common.util;

import android.os.Looper;

public class ThreadUtils {
    private ThreadUtils() {
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
