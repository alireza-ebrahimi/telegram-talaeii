package com.google.android.gms.internal;

final class zzfgx {
    private final byte[] buffer;
    private final zzfhg zzpob;

    private zzfgx(int i) {
        this.buffer = new byte[i];
        this.zzpob = zzfhg.zzbc(this.buffer);
    }

    public final zzfgs zzcxv() {
        this.zzpob.zzcyx();
        return new zzfgz(this.buffer);
    }

    public final zzfhg zzcxw() {
        return this.zzpob;
    }
}
