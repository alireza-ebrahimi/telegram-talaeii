package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.Map;

final class zzju implements zzfn {
    private final /* synthetic */ zzjs zzarf;
    private final /* synthetic */ String zzarg;

    zzju(zzjs zzjs, String str) {
        this.zzarf = zzjs;
        this.zzarg = str;
    }

    public final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        this.zzarf.zza(i, th, bArr, this.zzarg);
    }
}
