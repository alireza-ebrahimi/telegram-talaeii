package com.google.android.gms.common.util;

import android.os.Build.VERSION;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzs {
    public static boolean isAtLeastN() {
        return VERSION.SDK_INT >= 24;
    }

    public static boolean isAtLeastO() {
        return VERSION.SDK_INT >= 26;
    }

    public static boolean zzanr() {
        return VERSION.SDK_INT >= 15;
    }

    public static boolean zzans() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean zzant() {
        return VERSION.SDK_INT >= 17;
    }

    public static boolean zzanu() {
        return VERSION.SDK_INT >= 18;
    }

    public static boolean zzanv() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean zzanw() {
        return VERSION.SDK_INT >= 20;
    }

    public static boolean zzanx() {
        return VERSION.SDK_INT >= 21;
    }
}
