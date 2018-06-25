package com.google.android.gms.internal;

import java.io.IOException;

public final class zzdnj extends zzflm<zzdnj> {
    private static volatile zzdnj[] zzlwn;
    public String name;
    public zzdnk zzlwo;

    public zzdnj() {
        this.name = "";
        this.zzlwo = null;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzdnj[] zzbmb() {
        if (zzlwn == null) {
            synchronized (zzflq.zzpvt) {
                if (zzlwn == null) {
                    zzlwn = new zzdnj[0];
                }
            }
        }
        return zzlwn;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdnj)) {
            return false;
        }
        zzdnj zzdnj = (zzdnj) obj;
        if (this.name == null) {
            if (zzdnj.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzdnj.name)) {
            return false;
        }
        if (this.zzlwo == null) {
            if (zzdnj.zzlwo != null) {
                return false;
            }
        } else if (!this.zzlwo.equals(zzdnj.zzlwo)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzdnj.zzpvl == null || zzdnj.zzpvl.isEmpty() : this.zzpvl.equals(zzdnj.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31);
        zzdnk zzdnk = this.zzlwo;
        hashCode = ((zzdnk == null ? 0 : zzdnk.hashCode()) + (hashCode * 31)) * 31;
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
                case 18:
                    if (this.zzlwo == null) {
                        this.zzlwo = new zzdnk();
                    }
                    zzflj.zza(this.zzlwo);
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
        zzflk.zzp(1, this.name);
        if (this.zzlwo != null) {
            zzflk.zza(2, this.zzlwo);
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq() + zzflk.zzq(1, this.name);
        return this.zzlwo != null ? zzq + zzflk.zzb(2, this.zzlwo) : zzq;
    }
}
