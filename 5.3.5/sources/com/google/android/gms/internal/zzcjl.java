package com.google.android.gms.internal;

public final class zzcjl {
    private final int priority;
    private /* synthetic */ zzcjj zzjkv;
    private final boolean zzjkw;
    private final boolean zzjkx;

    zzcjl(zzcjj zzcjj, int i, boolean z, boolean z2) {
        this.zzjkv = zzcjj;
        this.priority = i;
        this.zzjkw = z;
        this.zzjkx = z2;
    }

    public final void log(String str) {
        this.zzjkv.zza(this.priority, this.zzjkw, this.zzjkx, str, null, null, null);
    }

    public final void zzd(String str, Object obj, Object obj2, Object obj3) {
        this.zzjkv.zza(this.priority, this.zzjkw, this.zzjkx, str, obj, obj2, obj3);
    }

    public final void zze(String str, Object obj, Object obj2) {
        this.zzjkv.zza(this.priority, this.zzjkw, this.zzjkx, str, obj, obj2, null);
    }

    public final void zzj(String str, Object obj) {
        this.zzjkv.zza(this.priority, this.zzjkw, this.zzjkx, str, obj, null, null);
    }
}
