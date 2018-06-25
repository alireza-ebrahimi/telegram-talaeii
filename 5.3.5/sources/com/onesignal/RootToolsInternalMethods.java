package com.onesignal;

import java.io.File;

class RootToolsInternalMethods {
    RootToolsInternalMethods() {
    }

    static boolean isRooted() {
        for (String where : new String[]{"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"}) {
            if (new File(where + "su").exists()) {
                return true;
            }
        }
        return false;
    }
}
