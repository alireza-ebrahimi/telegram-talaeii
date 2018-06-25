package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcod extends zzflm<zzcod> {
    public zzcoe[] zzjun;

    public zzcod() {
        this.zzjun = zzcoe.zzbdb();
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcod)) {
            return false;
        }
        zzcod zzcod = (zzcod) obj;
        return !zzflq.equals(this.zzjun, zzcod.zzjun) ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcod.zzpvl == null || zzcod.zzpvl.isEmpty() : this.zzpvl.equals(zzcod.zzpvl);
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + 527) * 31) + zzflq.hashCode(this.zzjun)) * 31;
        int hashCode2 = (this.zzpvl == null || this.zzpvl.isEmpty()) ? 0 : this.zzpvl.hashCode();
        return hashCode2 + hashCode;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            switch (zzcxx) {
                case 0:
                    break;
                case 10:
                    int zzb = zzflv.zzb(zzflj, 10);
                    zzcxx = this.zzjun == null ? 0 : this.zzjun.length;
                    Object obj = new zzcoe[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzjun, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzcoe();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzcoe();
                    zzflj.zza(obj[zzcxx]);
                    this.zzjun = obj;
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
        if (this.zzjun != null && this.zzjun.length > 0) {
            for (zzfls zzfls : this.zzjun) {
                if (zzfls != null) {
                    zzflk.zza(1, zzfls);
                }
            }
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.zzjun != null && this.zzjun.length > 0) {
            for (zzfls zzfls : this.zzjun) {
                if (zzfls != null) {
                    zzq += zzflk.zzb(1, zzfls);
                }
            }
        }
        return zzq;
    }
}
