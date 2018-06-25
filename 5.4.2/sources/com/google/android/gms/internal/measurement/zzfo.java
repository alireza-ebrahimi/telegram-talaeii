package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;
import java.util.List;
import java.util.Map;

final class zzfo implements Runnable {
    private final String packageName;
    private final int status;
    private final zzfn zzajq;
    private final Throwable zzajr;
    private final byte[] zzajs;
    private final Map<String, List<String>> zzajt;

    private zzfo(String str, zzfn zzfn, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        Preconditions.checkNotNull(zzfn);
        this.zzajq = zzfn;
        this.status = i;
        this.zzajr = th;
        this.zzajs = bArr;
        this.packageName = str;
        this.zzajt = map;
    }

    public final void run() {
        this.zzajq.zza(this.packageName, this.status, this.zzajr, this.zzajs, this.zzajt);
    }
}
