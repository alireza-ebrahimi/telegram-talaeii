package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnt extends zzflm<zzcnt> {
    private static volatile zzcnt[] zzjtc;
    public zzcnw zzjtd;
    public zzcnu zzjte;
    public Boolean zzjtf;
    public String zzjtg;

    public zzcnt() {
        this.zzjtd = null;
        this.zzjte = null;
        this.zzjtf = null;
        this.zzjtg = null;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzcnt[] zzbcu() {
        if (zzjtc == null) {
            synchronized (zzflq.zzpvt) {
                if (zzjtc == null) {
                    zzjtc = new zzcnt[0];
                }
            }
        }
        return zzjtc;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnt)) {
            return false;
        }
        zzcnt zzcnt = (zzcnt) obj;
        if (this.zzjtd == null) {
            if (zzcnt.zzjtd != null) {
                return false;
            }
        } else if (!this.zzjtd.equals(zzcnt.zzjtd)) {
            return false;
        }
        if (this.zzjte == null) {
            if (zzcnt.zzjte != null) {
                return false;
            }
        } else if (!this.zzjte.equals(zzcnt.zzjte)) {
            return false;
        }
        if (this.zzjtf == null) {
            if (zzcnt.zzjtf != null) {
                return false;
            }
        } else if (!this.zzjtf.equals(zzcnt.zzjtf)) {
            return false;
        }
        if (this.zzjtg == null) {
            if (zzcnt.zzjtg != null) {
                return false;
            }
        } else if (!this.zzjtg.equals(zzcnt.zzjtg)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcnt.zzpvl == null || zzcnt.zzpvl.isEmpty() : this.zzpvl.equals(zzcnt.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = getClass().getName().hashCode() + 527;
        zzcnw zzcnw = this.zzjtd;
        hashCode = (zzcnw == null ? 0 : zzcnw.hashCode()) + (hashCode * 31);
        zzcnu zzcnu = this.zzjte;
        hashCode = ((this.zzjtg == null ? 0 : this.zzjtg.hashCode()) + (((this.zzjtf == null ? 0 : this.zzjtf.hashCode()) + (((zzcnu == null ? 0 : zzcnu.hashCode()) + (hashCode * 31)) * 31)) * 31)) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i = this.zzpvl.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            switch (zzcxx) {
                case 0:
                    break;
                case 10:
                    if (this.zzjtd == null) {
                        this.zzjtd = new zzcnw();
                    }
                    zzflj.zza(this.zzjtd);
                    continue;
                case 18:
                    if (this.zzjte == null) {
                        this.zzjte = new zzcnu();
                    }
                    zzflj.zza(this.zzjte);
                    continue;
                case 24:
                    this.zzjtf = Boolean.valueOf(zzflj.zzcyd());
                    continue;
                case 34:
                    this.zzjtg = zzflj.readString();
                    continue;
                default:
                    if (!super.zza(zzflj, zzcxx)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (this.zzjtd != null) {
            zzflk.zza(1, this.zzjtd);
        }
        if (this.zzjte != null) {
            zzflk.zza(2, this.zzjte);
        }
        if (this.zzjtf != null) {
            zzflk.zzl(3, this.zzjtf.booleanValue());
        }
        if (this.zzjtg != null) {
            zzflk.zzp(4, this.zzjtg);
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.zzjtd != null) {
            zzq += zzflk.zzb(1, this.zzjtd);
        }
        if (this.zzjte != null) {
            zzq += zzflk.zzb(2, this.zzjte);
        }
        if (this.zzjtf != null) {
            this.zzjtf.booleanValue();
            zzq += zzflk.zzlw(3) + 1;
        }
        return this.zzjtg != null ? zzq + zzflk.zzq(4, this.zzjtg) : zzq;
    }
}
