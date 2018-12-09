package com.google.android.gms.common.util;

import android.os.Build.VERSION;
import android.support.v4.p014d.C0432c;

@VisibleForTesting
public final class PlatformVersion {
    private PlatformVersion() {
    }

    public static boolean isAtLeastFroyo() {
        return true;
    }

    public static boolean isAtLeastGingerbread() {
        return true;
    }

    public static boolean isAtLeastGingerbreadMR1() {
        return true;
    }

    public static boolean isAtLeastHoneycomb() {
        return true;
    }

    public static boolean isAtLeastHoneycombMR1() {
        return true;
    }

    public static boolean isAtLeastHoneycombMR2() {
        return true;
    }

    public static boolean isAtLeastIceCreamSandwich() {
        return true;
    }

    public static boolean isAtLeastIceCreamSandwichMR1() {
        return VERSION.SDK_INT >= 15;
    }

    public static boolean isAtLeastJellyBean() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean isAtLeastJellyBeanMR1() {
        return VERSION.SDK_INT >= 17;
    }

    public static boolean isAtLeastJellyBeanMR2() {
        return VERSION.SDK_INT >= 18;
    }

    @Deprecated
    public static boolean isAtLeastKeyLimePie() {
        return isAtLeastKitKat();
    }

    public static boolean isAtLeastKitKat() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean isAtLeastKitKatWatch() {
        return VERSION.SDK_INT >= 20;
    }

    @Deprecated
    public static boolean isAtLeastL() {
        return isAtLeastLollipop();
    }

    public static boolean isAtLeastLollipop() {
        return VERSION.SDK_INT >= 21;
    }

    public static boolean isAtLeastLollipopMR1() {
        return VERSION.SDK_INT >= 22;
    }

    public static boolean isAtLeastM() {
        return VERSION.SDK_INT >= 23;
    }

    public static boolean isAtLeastN() {
        return VERSION.SDK_INT >= 24;
    }

    public static boolean isAtLeastNMR1() {
        return VERSION.SDK_INT >= 25;
    }

    public static boolean isAtLeastO() {
        return VERSION.SDK_INT >= 26;
    }

    public static boolean isAtLeastOMR1() {
        return VERSION.SDK_INT >= 27;
    }

    public static boolean isAtLeastP() {
        return C0432c.isAtLeastP();
    }
}
