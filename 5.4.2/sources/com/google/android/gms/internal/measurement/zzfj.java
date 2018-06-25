package com.google.android.gms.internal.measurement;

public final class zzfj {
    private final int priority;
    private final /* synthetic */ zzfh zzajl;
    private final boolean zzajm;
    private final boolean zzajn;

    zzfj(zzfh zzfh, int i, boolean z, boolean z2) {
        this.zzajl = zzfh;
        this.priority = i;
        this.zzajm = z;
        this.zzajn = z2;
    }

    public final void log(String str) {
        this.zzajl.zza(this.priority, this.zzajm, this.zzajn, str, null, null, null);
    }

    public final void zzd(String str, Object obj, Object obj2, Object obj3) {
        this.zzajl.zza(this.priority, this.zzajm, this.zzajn, str, obj, obj2, obj3);
    }

    public final void zze(String str, Object obj, Object obj2) {
        this.zzajl.zza(this.priority, this.zzajm, this.zzajn, str, obj, obj2, null);
    }

    public final void zzg(String str, Object obj) {
        this.zzajl.zza(this.priority, this.zzajm, this.zzajn, str, obj, null, null);
    }
}
