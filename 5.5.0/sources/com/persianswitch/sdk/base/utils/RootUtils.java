package com.persianswitch.sdk.base.utils;

import java.io.File;

public final class RootUtils {
    /* renamed from: a */
    public static boolean m10764a() {
        return m10765a("su");
    }

    /* renamed from: a */
    private static boolean m10765a(String str) {
        try {
            for (String str2 : new String[]{"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"}) {
                if (new File(str2 + str).exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
