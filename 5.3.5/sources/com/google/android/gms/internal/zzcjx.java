package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

public final class zzcjx {
    private final String key;
    private long value;
    private boolean zzjmh;
    private /* synthetic */ zzcju zzjmi;
    private final long zzjmj;

    public zzcjx(zzcju zzcju, String str, long j) {
        this.zzjmi = zzcju;
        zzbq.zzgv(str);
        this.key = str;
        this.zzjmj = j;
    }

    @WorkerThread
    public final long get() {
        if (!this.zzjmh) {
            this.zzjmh = true;
            this.value = this.zzjmi.zzbbd().getLong(this.key, this.zzjmj);
        }
        return this.value;
    }

    @WorkerThread
    public final void set(long j) {
        Editor edit = this.zzjmi.zzbbd().edit();
        edit.putLong(this.key, j);
        edit.apply();
        this.value = j;
    }
}
