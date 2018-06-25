package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcnx extends zzflm<zzcnx> {
    private static volatile zzcnx[] zzjtt;
    public String name;
    public Boolean zzjtu;
    public Boolean zzjtv;
    public Integer zzjtw;

    public zzcnx() {
        this.name = null;
        this.zzjtu = null;
        this.zzjtv = null;
        this.zzjtw = null;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzcnx[] zzbcw() {
        if (zzjtt == null) {
            synchronized (zzflq.zzpvt) {
                if (zzjtt == null) {
                    zzjtt = new zzcnx[0];
                }
            }
        }
        return zzjtt;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcnx)) {
            return false;
        }
        zzcnx zzcnx = (zzcnx) obj;
        if (this.name == null) {
            if (zzcnx.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzcnx.name)) {
            return false;
        }
        if (this.zzjtu == null) {
            if (zzcnx.zzjtu != null) {
                return false;
            }
        } else if (!this.zzjtu.equals(zzcnx.zzjtu)) {
            return false;
        }
        if (this.zzjtv == null) {
            if (zzcnx.zzjtv != null) {
                return false;
            }
        } else if (!this.zzjtv.equals(zzcnx.zzjtv)) {
            return false;
        }
        if (this.zzjtw == null) {
            if (zzcnx.zzjtw != null) {
                return false;
            }
        } else if (!this.zzjtw.equals(zzcnx.zzjtw)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcnx.zzpvl == null || zzcnx.zzpvl.isEmpty() : this.zzpvl.equals(zzcnx.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzjtw == null ? 0 : this.zzjtw.hashCode()) + (((this.zzjtv == null ? 0 : this.zzjtv.hashCode()) + (((this.zzjtu == null ? 0 : this.zzjtu.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31;
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
                    this.name = zzflj.readString();
                    continue;
                case 16:
                    this.zzjtu = Boolean.valueOf(zzflj.zzcyd());
                    continue;
                case 24:
                    this.zzjtv = Boolean.valueOf(zzflj.zzcyd());
                    continue;
                case 32:
                    this.zzjtw = Integer.valueOf(zzflj.zzcym());
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
        if (this.name != null) {
            zzflk.zzp(1, this.name);
        }
        if (this.zzjtu != null) {
            zzflk.zzl(2, this.zzjtu.booleanValue());
        }
        if (this.zzjtv != null) {
            zzflk.zzl(3, this.zzjtv.booleanValue());
        }
        if (this.zzjtw != null) {
            zzflk.zzad(4, this.zzjtw.intValue());
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.name != null) {
            zzq += zzflk.zzq(1, this.name);
        }
        if (this.zzjtu != null) {
            this.zzjtu.booleanValue();
            zzq += zzflk.zzlw(2) + 1;
        }
        if (this.zzjtv != null) {
            this.zzjtv.booleanValue();
            zzq += zzflk.zzlw(3) + 1;
        }
        return this.zzjtw != null ? zzq + zzflk.zzag(4, this.zzjtw.intValue()) : zzq;
    }
}
