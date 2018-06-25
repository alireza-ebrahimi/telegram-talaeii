package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public abstract class zzag {
    private static final Object zzggs = new Object();
    private static zzag zzggt;

    public static zzag zzcp(Context context) {
        synchronized (zzggs) {
            if (zzggt == null) {
                zzggt = new zzai(context.getApplicationContext());
            }
        }
        return zzggt;
    }

    public final void zza(String str, String str2, int i, ServiceConnection serviceConnection, String str3) {
        zzb(new zzah(str, str2, i), serviceConnection, str3);
    }

    public final boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        return zza(new zzah(componentName, TsExtractor.TS_STREAM_TYPE_AC3), serviceConnection, str);
    }

    protected abstract boolean zza(zzah zzah, ServiceConnection serviceConnection, String str);

    public final void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        zzb(new zzah(componentName, TsExtractor.TS_STREAM_TYPE_AC3), serviceConnection, str);
    }

    protected abstract void zzb(zzah zzah, ServiceConnection serviceConnection, String str);
}
