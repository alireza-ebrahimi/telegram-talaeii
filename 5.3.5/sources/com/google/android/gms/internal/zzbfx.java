package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;

@Hide
public class zzbfx<T> {
    private static String READ_PERMISSION = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    private static final Object sLock = new Object();
    private static zzbgd zzgby = null;
    private static int zzgbz = 0;
    private String zzbkr;
    private T zzbks;
    private T zzgca = null;

    protected zzbfx(String str, T t) {
        this.zzbkr = str;
        this.zzbks = t;
    }

    public static zzbfx<Float> zza(String str, Float f) {
        return new zzbgb(str, f);
    }

    public static zzbfx<Integer> zza(String str, Integer num) {
        return new zzbga(str, num);
    }

    public static zzbfx<Long> zza(String str, Long l) {
        return new zzbfz(str, l);
    }

    public static zzbfx<Boolean> zze(String str, boolean z) {
        return new zzbfy(str, Boolean.valueOf(z));
    }

    public static zzbfx<String> zzs(String str, String str2) {
        return new zzbgc(str, str2);
    }
}
