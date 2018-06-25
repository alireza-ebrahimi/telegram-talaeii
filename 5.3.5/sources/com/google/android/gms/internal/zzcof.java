package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcof extends zzflm<zzcof> {
    public long[] zzjvo;
    public long[] zzjvp;

    public zzcof() {
        this.zzjvo = zzflv.zzpvz;
        this.zzjvp = zzflv.zzpvz;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcof)) {
            return false;
        }
        zzcof zzcof = (zzcof) obj;
        return !zzflq.equals(this.zzjvo, zzcof.zzjvo) ? false : !zzflq.equals(this.zzjvp, zzcof.zzjvp) ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcof.zzpvl == null || zzcof.zzpvl.isEmpty() : this.zzpvl.equals(zzcof.zzpvl);
    }

    public final int hashCode() {
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + zzflq.hashCode(this.zzjvo)) * 31) + zzflq.hashCode(this.zzjvp)) * 31;
        int hashCode2 = (this.zzpvl == null || this.zzpvl.isEmpty()) ? 0 : this.zzpvl.hashCode();
        return hashCode2 + hashCode;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            int zzb;
            Object obj;
            int zzli;
            Object obj2;
            switch (zzcxx) {
                case 0:
                    break;
                case 8:
                    zzb = zzflv.zzb(zzflj, 8);
                    zzcxx = this.zzjvo == null ? 0 : this.zzjvo.length;
                    obj = new long[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjvo, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.zzcyr();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.zzcyr();
                    this.zzjvo = obj;
                    continue;
                case 10:
                    zzli = zzflj.zzli(zzflj.zzcym());
                    zzb = zzflj.getPosition();
                    zzcxx = 0;
                    while (zzflj.zzcyo() > 0) {
                        zzflj.zzcyr();
                        zzcxx++;
                    }
                    zzflj.zzmw(zzb);
                    zzb = this.zzjvo == null ? 0 : this.zzjvo.length;
                    obj2 = new long[(zzcxx + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzjvo, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = zzflj.zzcyr();
                        zzb++;
                    }
                    this.zzjvo = obj2;
                    zzflj.zzlj(zzli);
                    continue;
                case 16:
                    zzb = zzflv.zzb(zzflj, 16);
                    zzcxx = this.zzjvp == null ? 0 : this.zzjvp.length;
                    obj = new long[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjvp, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = zzflj.zzcyr();
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = zzflj.zzcyr();
                    this.zzjvp = obj;
                    continue;
                case 18:
                    zzli = zzflj.zzli(zzflj.zzcym());
                    zzb = zzflj.getPosition();
                    zzcxx = 0;
                    while (zzflj.zzcyo() > 0) {
                        zzflj.zzcyr();
                        zzcxx++;
                    }
                    zzflj.zzmw(zzb);
                    zzb = this.zzjvp == null ? 0 : this.zzjvp.length;
                    obj2 = new long[(zzcxx + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzjvp, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = zzflj.zzcyr();
                        zzb++;
                    }
                    this.zzjvp = obj2;
                    zzflj.zzlj(zzli);
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
        int i = 0;
        if (this.zzjvo != null && this.zzjvo.length > 0) {
            for (long zza : this.zzjvo) {
                zzflk.zza(1, zza);
            }
        }
        if (this.zzjvp != null && this.zzjvp.length > 0) {
            while (i < this.zzjvp.length) {
                zzflk.zza(2, this.zzjvp[i]);
                i++;
            }
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int i;
        int i2;
        int i3 = 0;
        int zzq = super.zzq();
        if (this.zzjvo == null || this.zzjvo.length <= 0) {
            i = zzq;
        } else {
            i2 = 0;
            for (long zzdj : this.zzjvo) {
                i2 += zzflk.zzdj(zzdj);
            }
            i = (zzq + i2) + (this.zzjvo.length * 1);
        }
        if (this.zzjvp == null || this.zzjvp.length <= 0) {
            return i;
        }
        i2 = 0;
        while (i3 < this.zzjvp.length) {
            i2 += zzflk.zzdj(this.zzjvp[i3]);
            i3++;
        }
        return (i + i2) + (this.zzjvp.length * 1);
    }
}
