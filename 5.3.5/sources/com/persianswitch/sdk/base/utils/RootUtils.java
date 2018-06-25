package com.persianswitch.sdk.base.utils;

import java.io.File;

public final class RootUtils {
    private static boolean findBinary(String binaryName) {
        try {
            for (String where : new String[]{"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"}) {
                if (new File(where + binaryName).exists()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isRooted() {
        return findBinary("su");
    }
}
