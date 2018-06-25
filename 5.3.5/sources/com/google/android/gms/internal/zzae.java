package com.google.android.gms.internal;

public class zzae extends Exception {
    private long zzad;
    private zzp zzbj;

    public zzae() {
        this.zzbj = null;
    }

    public zzae(zzp zzp) {
        this.zzbj = zzp;
    }

    public zzae(String str) {
        super(str);
        this.zzbj = null;
    }

    public zzae(Throwable th) {
        super(th);
        this.zzbj = null;
    }

    final void zza(long j) {
        this.zzad = j;
    }
}
