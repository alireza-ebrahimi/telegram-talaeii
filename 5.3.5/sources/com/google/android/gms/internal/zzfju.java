package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;

final class zzfju extends InputStream {
    private int mark;
    private zzfjt zzpsa;
    private zzfgy zzpsb;
    private int zzpsc;
    private int zzpsd;
    private int zzpse;
    private /* synthetic */ zzfjq zzpsf;

    public zzfju(zzfjq zzfjq) {
        this.zzpsf = zzfjq;
        initialize();
    }

    private final void initialize() {
        this.zzpsa = new zzfjt(this.zzpsf);
        this.zzpsb = (zzfgy) this.zzpsa.next();
        this.zzpsc = this.zzpsb.size();
        this.zzpsd = 0;
        this.zzpse = 0;
    }

    private final void zzdbj() {
        if (this.zzpsb != null && this.zzpsd == this.zzpsc) {
            this.zzpse += this.zzpsc;
            this.zzpsd = 0;
            if (this.zzpsa.hasNext()) {
                this.zzpsb = (zzfgy) this.zzpsa.next();
                this.zzpsc = this.zzpsb.size();
                return;
            }
            this.zzpsb = null;
            this.zzpsc = 0;
        }
    }

    private final int zzk(byte[] bArr, int i, int i2) {
        int i3 = i2;
        int i4 = i;
        while (i3 > 0) {
            zzdbj();
            if (this.zzpsb == null) {
                if (i3 == i2) {
                    return -1;
                }
                return i2 - i3;
            }
            int min = Math.min(this.zzpsc - this.zzpsd, i3);
            if (bArr != null) {
                this.zzpsb.zza(bArr, this.zzpsd, i4, min);
                i4 += min;
            }
            this.zzpsd += min;
            i3 -= min;
        }
        return i2 - i3;
    }

    public final int available() throws IOException {
        return this.zzpsf.size() - (this.zzpse + this.zzpsd);
    }

    public final void mark(int i) {
        this.mark = this.zzpse + this.zzpsd;
    }

    public final boolean markSupported() {
        return true;
    }

    public final int read() throws IOException {
        zzdbj();
        if (this.zzpsb == null) {
            return -1;
        }
        zzfgs zzfgs = this.zzpsb;
        int i = this.zzpsd;
        this.zzpsd = i + 1;
        return zzfgs.zzld(i) & 255;
    }

    public final int read(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new NullPointerException();
        } else if (i >= 0 && i2 >= 0 && i2 <= bArr.length - i) {
            return zzk(bArr, i, i2);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public final synchronized void reset() {
        initialize();
        zzk(null, 0, this.mark);
    }

    public final long skip(long j) {
        if (j < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (j > 2147483647L) {
            j = 2147483647L;
        }
        return (long) zzk(null, 0, (int) j);
    }
}
