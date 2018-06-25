package com.google.android.gms.internal.measurement;

class zzze extends zzzd {
    protected final byte[] zzbrm;

    zzze(byte[] bArr) {
        this.zzbrm = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzyy)) {
            return false;
        }
        if (size() != ((zzyy) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzze)) {
            return obj.equals(this);
        }
        zzze zzze = (zzze) obj;
        int zztg = zztg();
        int zztg2 = zzze.zztg();
        return (zztg == 0 || zztg2 == 0 || zztg == zztg2) ? zza((zzze) obj, 0, size()) : false;
    }

    public int size() {
        return this.zzbrm.length;
    }

    protected final int zza(int i, int i2, int i3) {
        return zzzt.zza(i, this.zzbrm, zzth(), i3);
    }

    final boolean zza(zzyy zzyy, int i, int i2) {
        if (i2 > zzyy.size()) {
            throw new IllegalArgumentException("Length too large: " + i2 + size());
        } else if (i2 > zzyy.size()) {
            throw new IllegalArgumentException("Ran off end of other: 0, " + i2 + ", " + zzyy.size());
        } else if (!(zzyy instanceof zzze)) {
            return zzyy.zzb(0, i2).equals(zzb(0, i2));
        } else {
            zzze zzze = (zzze) zzyy;
            byte[] bArr = this.zzbrm;
            byte[] bArr2 = zzze.zzbrm;
            int zzth = zzth() + i2;
            int zzth2 = zzth();
            int zzth3 = zzze.zzth();
            while (zzth2 < zzth) {
                if (bArr[zzth2] != bArr2[zzth3]) {
                    return false;
                }
                zzth2++;
                zzth3++;
            }
            return true;
        }
    }

    public byte zzae(int i) {
        return this.zzbrm[i];
    }

    public final zzyy zzb(int i, int i2) {
        int zzb = zzyy.zzb(0, i2, size());
        return zzb == 0 ? zzyy.zzbrh : new zzzb(this.zzbrm, zzth(), zzb);
    }

    protected int zzth() {
        return 0;
    }
}
