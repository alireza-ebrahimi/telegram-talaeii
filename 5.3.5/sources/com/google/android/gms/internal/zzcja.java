package com.google.android.gms.internal;

public final class zzcja<V> {
    private final String key;
    private final V zzjjw;
    private final V zzjjx;

    private zzcja(String str, V v, V v2) {
        this.zzjjw = v;
        this.zzjjx = v2;
        this.key = str;
    }

    static zzcja<Long> zzb(String str, long j, long j2) {
        zzcja<Long> zzcja = new zzcja(str, Long.valueOf(j), Long.valueOf(j2));
        zzciz.zzjid.add(zzcja);
        return zzcja;
    }

    static zzcja<Boolean> zzb(String str, boolean z, boolean z2) {
        zzcja<Boolean> zzcja = new zzcja(str, Boolean.valueOf(false), Boolean.valueOf(false));
        zzciz.zzjie.add(zzcja);
        return zzcja;
    }

    static zzcja<String> zzj(String str, String str2, String str3) {
        zzcja<String> zzcja = new zzcja(str, str2, str3);
        zzciz.zzjif.add(zzcja);
        return zzcja;
    }

    static zzcja<Integer> zzm(String str, int i, int i2) {
        zzcja<Integer> zzcja = new zzcja(str, Integer.valueOf(i), Integer.valueOf(i2));
        zzciz.zzjic.add(zzcja);
        return zzcja;
    }

    public final V get() {
        return this.zzjjw;
    }

    public final V get(V v) {
        return v != null ? v : this.zzjjw;
    }

    public final String getKey() {
        return this.key;
    }
}
