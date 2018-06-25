package com.google.android.gms.internal.measurement;

import android.net.Uri;

public final class zzxe {
    private final String zzboc;
    private final Uri zzbod;
    private final String zzboe;
    private final String zzbof;
    private final boolean zzbog;
    private final boolean zzboh;

    public zzxe(Uri uri) {
        this(null, uri, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, false, false);
    }

    private zzxe(String str, Uri uri, String str2, String str3, boolean z, boolean z2) {
        this.zzboc = null;
        this.zzbod = uri;
        this.zzboe = str2;
        this.zzbof = str3;
        this.zzbog = false;
        this.zzboh = false;
    }

    public final zzwu<Double> zzb(String str, double d) {
        return zzwu.zza(this, str, d);
    }

    public final zzwu<Integer> zzd(String str, int i) {
        return zzwu.zza(this, str, i);
    }

    public final zzwu<Long> zze(String str, long j) {
        return zzwu.zza(this, str, j);
    }

    public final zzwu<Boolean> zzf(String str, boolean z) {
        return zzwu.zza(this, str, z);
    }

    public final zzwu<String> zzv(String str, String str2) {
        return zzwu.zza(this, str, str2);
    }
}
