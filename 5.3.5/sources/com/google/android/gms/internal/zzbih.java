package com.google.android.gms.internal;

import android.content.Context;

public final class zzbih {
    private static zzbih zzglt = new zzbih();
    private zzbig zzgls = null;

    private final synchronized zzbig zzdc(Context context) {
        if (this.zzgls == null) {
            if (context.getApplicationContext() != null) {
                context = context.getApplicationContext();
            }
            this.zzgls = new zzbig(context);
        }
        return this.zzgls;
    }

    public static zzbig zzdd(Context context) {
        return zzglt.zzdc(context);
    }
}
