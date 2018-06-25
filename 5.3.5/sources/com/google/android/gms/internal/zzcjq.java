package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;
import java.util.List;
import java.util.Map;

@WorkerThread
final class zzcjq implements Runnable {
    private final String packageName;
    private final int status;
    private final zzcjp zzjky;
    private final Throwable zzjkz;
    private final byte[] zzjla;
    private final Map<String, List<String>> zzjlb;

    private zzcjq(String str, zzcjp zzcjp, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        zzbq.checkNotNull(zzcjp);
        this.zzjky = zzcjp;
        this.status = i;
        this.zzjkz = th;
        this.zzjla = bArr;
        this.packageName = str;
        this.zzjlb = map;
    }

    public final void run() {
        this.zzjky.zza(this.packageName, this.status, this.zzjkz, this.zzjla, this.zzjlb);
    }
}
