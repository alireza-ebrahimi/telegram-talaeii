package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

public final class zzcjz {
    private final String key;
    private String value;
    private boolean zzjmh;
    private /* synthetic */ zzcju zzjmi;
    private final String zzjmo = null;

    public zzcjz(zzcju zzcju, String str, String str2) {
        this.zzjmi = zzcju;
        zzbq.zzgv(str);
        this.key = str;
    }

    @WorkerThread
    public final String zzbbj() {
        if (!this.zzjmh) {
            this.zzjmh = true;
            this.value = this.zzjmi.zzbbd().getString(this.key, null);
        }
        return this.value;
    }

    @WorkerThread
    public final void zzjy(String str) {
        if (!zzcno.zzas(str, this.value)) {
            Editor edit = this.zzjmi.zzbbd().edit();
            edit.putString(this.key, str);
            edit.apply();
            this.value = str;
        }
    }
}
