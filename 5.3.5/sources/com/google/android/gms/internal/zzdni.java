package com.google.android.gms.internal;

import java.io.IOException;

public final class zzdni extends zzflm<zzdni> {
    public zzdnj[] zzlwm;

    public zzdni() {
        this.zzlwm = zzdnj.zzbmb();
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzdni zzad(byte[] bArr) throws zzflr {
        return (zzdni) zzfls.zza(new zzdni(), bArr);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdni)) {
            return false;
        }
        zzdni zzdni = (zzdni) obj;
        return !zzflq.equals(this.zzlwm, zzdni.zzlwm) ? false : (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzdni.zzpvl == null || zzdni.zzpvl.isEmpty() : this.zzpvl.equals(zzdni.zzpvl);
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + 527) * 31) + zzflq.hashCode(this.zzlwm)) * 31;
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
                    zzcxx = this.zzlwm == null ? 0 : this.zzlwm.length;
                    Object obj = new zzdnj[(zzb + zzcxx)];
                    if (zzcxx != 0) {
                        System.arraycopy(this.zzlwm, 0, obj, 0, zzcxx);
                    }
                    while (zzcxx < obj.length - 1) {
                        obj[zzcxx] = new zzdnj();
                        zzflj.zza(obj[zzcxx]);
                        zzflj.zzcxx();
                        zzcxx++;
                    }
                    obj[zzcxx] = new zzdnj();
                    zzflj.zza(obj[zzcxx]);
                    this.zzlwm = obj;
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
        if (this.zzlwm != null && this.zzlwm.length > 0) {
            for (zzfls zzfls : this.zzlwm) {
                if (zzfls != null) {
                    zzflk.zza(1, zzfls);
                }
            }
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.zzlwm != null && this.zzlwm.length > 0) {
            for (zzfls zzfls : this.zzlwm) {
                if (zzfls != null) {
                    zzq += zzflk.zzb(1, zzfls);
                }
            }
        }
        return zzq;
    }
}
