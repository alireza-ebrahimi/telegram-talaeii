package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

public final class zzcjw {
    private final String key;
    private boolean value;
    private final boolean zzjmg = true;
    private boolean zzjmh;
    private /* synthetic */ zzcju zzjmi;

    public zzcjw(zzcju zzcju, String str, boolean z) {
        this.zzjmi = zzcju;
        zzbq.zzgv(str);
        this.key = str;
    }

    @WorkerThread
    public final boolean get() {
        if (!this.zzjmh) {
            this.zzjmh = true;
            this.value = this.zzjmi.zzbbd().getBoolean(this.key, this.zzjmg);
        }
        return this.value;
    }

    @WorkerThread
    public final void set(boolean z) {
        Editor edit = this.zzjmi.zzbbd().edit();
        edit.putBoolean(this.key, z);
        edit.apply();
        this.value = z;
    }
}
