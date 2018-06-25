package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zzs;

public final class zzbif {
    private static Context zzglq;
    private static Boolean zzglr;

    public static synchronized boolean zzdb(Context context) {
        boolean booleanValue;
        synchronized (zzbif.class) {
            Context applicationContext = context.getApplicationContext();
            if (zzglq == null || zzglr == null || zzglq != applicationContext) {
                zzglr = null;
                if (zzs.isAtLeastO()) {
                    zzglr = Boolean.valueOf(applicationContext.getPackageManager().isInstantApp());
                } else {
                    try {
                        context.getClassLoader().loadClass("com.google.android.instantapps.supervisor.InstantAppsRuntime");
                        zzglr = Boolean.valueOf(true);
                    } catch (ClassNotFoundException e) {
                        zzglr = Boolean.valueOf(false);
                    }
                }
                zzglq = applicationContext;
                booleanValue = zzglr.booleanValue();
            } else {
                booleanValue = zzglr.booleanValue();
            }
        }
        return booleanValue;
    }
}
