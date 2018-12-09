package com.google.android.gms.internal.clearcut;

import java.nio.charset.Charset;

class zzbi extends zzbh {
    protected final byte[] zzfp;

    zzbi(byte[] bArr) {
        this.zzfp = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbb)) {
            return false;
        }
        if (size() != ((zzbb) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzbi)) {
            return obj.equals(this);
        }
        zzbi zzbi = (zzbi) obj;
        int zzab = zzab();
        int zzab2 = zzbi.zzab();
        return (zzab == 0 || zzab2 == 0 || zzab == zzab2) ? zza((zzbi) obj, 0, size()) : false;
    }

    public int size() {
        return this.zzfp.length;
    }

    protected final int zza(int i, int i2, int i3) {
        return zzci.zza(i, this.zzfp, zzac(), i3);
    }

    public final zzbb zza(int i, int i2) {
        int zzb = zzbb.zzb(0, i2, size());
        return zzb == 0 ? zzbb.zzfi : new zzbe(this.zzfp, zzac(), zzb);
    }

    protected final String zza(Charset charset) {
        return new String(this.zzfp, zzac(), size(), charset);
    }

    final void zza(zzba zzba) {
        zzba.zza(this.zzfp, zzac(), size());
    }

    final boolean zza(zzbb zzbb, int i, int i2) {
        if (i2 > zzbb.size()) {
            throw new IllegalArgumentException("Length too large: " + i2 + size());
        } else if (i2 > zzbb.size()) {
            throw new IllegalArgumentException("Ran off end of other: 0, " + i2 + ", " + zzbb.size());
        } else if (!(zzbb instanceof zzbi)) {
            return zzbb.zza(0, i2).equals(zza(0, i2));
        } else {
            zzbi zzbi = (zzbi) zzbb;
            byte[] bArr = this.zzfp;
            byte[] bArr2 = zzbi.zzfp;
            int zzac = zzac() + i2;
            int zzac2 = zzac();
            int zzac3 = zzbi.zzac();
            while (zzac2 < zzac) {
                if (bArr[zzac2] != bArr2[zzac3]) {
                    return false;
                }
                zzac2++;
                zzac3++;
            }
            return true;
        }
    }

    public final boolean zzaa() {
        int zzac = zzac();
        return zzff.zze(this.zzfp, zzac, size() + zzac);
    }

    protected int zzac() {
        return 0;
    }

    public byte zzj(int i) {
        return this.zzfp[i];
    }
}
