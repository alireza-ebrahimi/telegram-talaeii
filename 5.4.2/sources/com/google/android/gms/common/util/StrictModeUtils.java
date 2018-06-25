package com.google.android.gms.common.util;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

public class StrictModeUtils {
    public static ThreadPolicy setDynamiteThreadPolicy() {
        StrictMode.noteSlowCall("gcore.dynamite");
        ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(ThreadPolicy.LAX);
        return threadPolicy;
    }
}
