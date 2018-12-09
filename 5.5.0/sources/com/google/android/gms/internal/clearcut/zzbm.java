package com.google.android.gms.internal.clearcut;

final class zzbm extends zzbk {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzfu;
    private int zzfv;
    private int zzfw;
    private int zzfx;

    private zzbm(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzfx = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i + i2;
        this.pos = i;
        this.zzfw = this.pos;
        this.zzfu = z;
    }

    public final int zzaf() {
        return this.pos - this.zzfw;
    }

    public final int zzl(int i) {
        if (i < 0) {
            throw new zzco("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int zzaf = zzaf() + i;
        int i2 = this.zzfx;
        if (zzaf > i2) {
            throw zzco.zzbl();
        }
        this.zzfx = zzaf;
        this.limit += this.zzfv;
        zzaf = this.limit - this.zzfw;
        if (zzaf > this.zzfx) {
            this.zzfv = zzaf - this.zzfx;
            this.limit -= this.zzfv;
        } else {
            this.zzfv = 0;
        }
        return i2;
    }
}
