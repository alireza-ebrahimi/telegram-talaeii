package com.google.android.gms.common.wrappers;

import android.content.Context;
import com.google.android.gms.common.util.VisibleForTesting;

public class Wrappers {
    private static Wrappers zzabb = new Wrappers();
    private PackageManagerWrapper zzaba = null;

    public static PackageManagerWrapper packageManager(Context context) {
        return zzabb.getPackageManagerWrapper(context);
    }

    @VisibleForTesting
    public static void resetForTests() {
        zzabb = new Wrappers();
    }

    public static void setWrappers(Wrappers wrappers) {
        zzabb = wrappers;
    }

    @VisibleForTesting
    public synchronized PackageManagerWrapper getPackageManagerWrapper(Context context) {
        if (this.zzaba == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.zzaba = new PackageManagerWrapper(context);
        }
        return this.zzaba;
    }
}
