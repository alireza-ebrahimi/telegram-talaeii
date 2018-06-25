package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public final class zzfmq extends zzflm<zzfmq> implements Cloneable {
    private byte[] zzpyq;
    private String zzpyr;
    private byte[][] zzpys;
    private boolean zzpyt;

    public zzfmq() {
        this.zzpyq = zzflv.zzpwe;
        this.zzpyr = "";
        this.zzpys = zzflv.zzpwd;
        this.zzpyt = false;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    private zzfmq zzddd() {
        try {
            zzfmq zzfmq = (zzfmq) super.zzdck();
            if (this.zzpys != null && this.zzpys.length > 0) {
                zzfmq.zzpys = (byte[][]) this.zzpys.clone();
            }
            return zzfmq;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzddd();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfmq)) {
            return false;
        }
        zzfmq zzfmq = (zzfmq) obj;
        if (!Arrays.equals(this.zzpyq, zzfmq.zzpyq)) {
            return false;
        }
        if (this.zzpyr == null) {
            if (zzfmq.zzpyr != null) {
                return false;
            }
        } else if (!this.zzpyr.equals(zzfmq.zzpyr)) {
            return false;
        }
        return !zzflq.zza(this.zzpys, zzfmq.zzpys) ? false : this.zzpyt != zzfmq.zzpyt ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzfmq.zzpvl == null || zzfmq.zzpvl.isEmpty() : this.zzpvl.equals(zzfmq.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzpyt ? 1231 : 1237) + (((((this.zzpyr == null ? 0 : this.zzpyr.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zzpyq)) * 31)) * 31) + zzflq.zzd(this.zzpys)) * 31)) * 31;
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
                    this.zzpyq = zzflj.readBytes();
                    continue;
                case 18:
                    int zzb = zzflv.zzb(zzflj, 18);
                    zzcxx = this.zzpys == null ? 0 : this.zzpys.length;
                    Object obj = new byte[(zzb + zzcxx)][];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzpys, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.readBytes();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.readBytes();
                    this.zzpys = obj;
                    continue;
                case 24:
                    this.zzpyt = zzflj.zzcyd();
                    continue;
                case 34:
                    this.zzpyr = zzflj.readString();
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
        if (!Arrays.equals(this.zzpyq, zzflv.zzpwe)) {
            zzflk.zzc(1, this.zzpyq);
        }
        if (this.zzpys != null && this.zzpys.length > 0) {
            for (byte[] bArr : this.zzpys) {
                if (bArr != null) {
                    zzflk.zzc(2, bArr);
                }
            }
        }
        if (this.zzpyt) {
            zzflk.zzl(3, this.zzpyt);
        }
        if (!(this.zzpyr == null || this.zzpyr.equals(""))) {
            zzflk.zzp(4, this.zzpyr);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzdck() throws CloneNotSupportedException {
        return (zzfmq) clone();
    }

    public final /* synthetic */ zzfls zzdcl() throws CloneNotSupportedException {
        return (zzfmq) clone();
    }

    protected final int zzq() {
        int i = 0;
        int zzq = super.zzq();
        if (!Arrays.equals(this.zzpyq, zzflv.zzpwe)) {
            zzq += zzflk.zzd(1, this.zzpyq);
        }
        if (this.zzpys != null && this.zzpys.length > 0) {
            int i2 = 0;
            int i3 = 0;
            while (i < this.zzpys.length) {
                byte[] bArr = this.zzpys[i];
                if (bArr != null) {
                    i3++;
                    i2 += zzflk.zzbg(bArr);
                }
                i++;
            }
            zzq = (zzq + i2) + (i3 * 1);
        }
        if (this.zzpyt) {
            zzq += zzflk.zzlw(3) + 1;
        }
        return (this.zzpyr == null || this.zzpyr.equals("")) ? zzq : zzq + zzflk.zzq(4, this.zzpyr);
    }
}
