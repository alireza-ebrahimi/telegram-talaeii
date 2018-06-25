package com.google.android.gms.common.wrappers;

import android.content.Context;
import com.google.android.gms.common.util.PlatformVersion;

public class InstantApps {
    private static Context zzaay;
    private static Boolean zzaaz;

    public static synchronized boolean isInstantApp(Context context) {
        boolean booleanValue;
        synchronized (InstantApps.class) {
            Context applicationContext = context.getApplicationContext();
            if (zzaay == null || zzaaz == null || zzaay != applicationContext) {
                zzaaz = null;
                if (PlatformVersion.isAtLeastO()) {
                    zzaaz = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
                } else {
                    try {
                        context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                        zzaaz = Boolean.valueOf(true);
                    } catch (ClassNotFoundException e) {
                        zzaaz = Boolean.valueOf(false);
                    }
                }
                zzaay = applicationContext;
                booleanValue = zzaaz.booleanValue();
            } else {
                booleanValue = zzaaz.booleanValue();
            }
        }
        return booleanValue;
    }
}
