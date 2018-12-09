package com.google.android.gms.internal.measurement;

final class zzzi extends zzzg {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzbrr;
    private int zzbrs;
    private int zzbrt;
    private int zzbru;

    private zzzi(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzbru = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i + i2;
        this.pos = i;
        this.zzbrt = this.pos;
        this.zzbrr = z;
    }

    private final void zztj() {
        this.limit += this.zzbrs;
        int i = this.limit - this.zzbrt;
        if (i > this.zzbru) {
            this.zzbrs = i - this.zzbru;
            this.limit -= this.zzbrs;
            return;
        }
        this.zzbrs = 0;
    }

    public final int zzaf(int i) {
        if (i < 0) {
            throw zzzv.zztw();
        }
        int zzti = zzti() + i;
        int i2 = this.zzbru;
        if (zzti > i2) {
            throw zzzv.zztv();
        }
        this.zzbru = zzti;
        zztj();
        return i2;
    }

    public final int zzti() {
        return this.pos - this.zzbrt;
    }
}
