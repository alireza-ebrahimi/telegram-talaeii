package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public abstract class zzcce<T> {
    private final int zzbkq;
    private final String zzbkr;
    private final T zzbks;

    private zzcce(int i, String str, T t) {
        this.zzbkq = i;
        this.zzbkr = str;
        this.zzbks = t;
        zzccp.zzasn().zza(this);
    }

    @Hide
    public static zzccg zzb(int i, String str, Boolean bool) {
        return new zzccg(0, str, bool);
    }

    @Hide
    public static zzcch zzb(int i, String str, int i2) {
        return new zzcch(0, str, Integer.valueOf(i2));
    }

    @Hide
    public static zzcci zzb(int i, String str, long j) {
        return new zzcci(0, str, Long.valueOf(j));
    }

    public final String getKey() {
        return this.zzbkr;
    }

    @Hide
    public final int getSource() {
        return this.zzbkq;
    }

    @Hide
    protected abstract T zza(zzccm zzccm);

    public final T zzje() {
        return this.zzbks;
    }
}
