package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcog extends zzflm<zzcog> {
    private static volatile zzcog[] zzjvq;
    public String name;
    public String zzgim;
    private Float zzjsk;
    public Double zzjsl;
    public Long zzjum;
    public Long zzjvr;

    public zzcog() {
        this.zzjvr = null;
        this.name = null;
        this.zzgim = null;
        this.zzjum = null;
        this.zzjsk = null;
        this.zzjsl = null;
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzcog[] zzbdc() {
        if (zzjvq == null) {
            synchronized (zzflq.zzpvt) {
                if (zzjvq == null) {
                    zzjvq = new zzcog[0];
                }
            }
        }
        return zzjvq;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcog)) {
            return false;
        }
        zzcog zzcog = (zzcog) obj;
        if (this.zzjvr == null) {
            if (zzcog.zzjvr != null) {
                return false;
            }
        } else if (!this.zzjvr.equals(zzcog.zzjvr)) {
            return false;
        }
        if (this.name == null) {
            if (zzcog.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzcog.name)) {
            return false;
        }
        if (this.zzgim == null) {
            if (zzcog.zzgim != null) {
                return false;
            }
        } else if (!this.zzgim.equals(zzcog.zzgim)) {
            return false;
        }
        if (this.zzjum == null) {
            if (zzcog.zzjum != null) {
                return false;
            }
        } else if (!this.zzjum.equals(zzcog.zzjum)) {
            return false;
        }
        if (this.zzjsk == null) {
            if (zzcog.zzjsk != null) {
                return false;
            }
        } else if (!this.zzjsk.equals(zzcog.zzjsk)) {
            return false;
        }
        if (this.zzjsl == null) {
            if (zzcog.zzjsl != null) {
                return false;
            }
        } else if (!this.zzjsl.equals(zzcog.zzjsl)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzcog.zzpvl == null || zzcog.zzpvl.isEmpty() : this.zzpvl.equals(zzcog.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzjsl == null ? 0 : this.zzjsl.hashCode()) + (((this.zzjsk == null ? 0 : this.zzjsk.hashCode()) + (((this.zzjum == null ? 0 : this.zzjum.hashCode()) + (((this.zzgim == null ? 0 : this.zzgim.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + (((this.zzjvr == null ? 0 : this.zzjvr.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
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
                case 8:
                    this.zzjvr = Long.valueOf(zzflj.zzcyr());
                    continue;
                case 18:
                    this.name = zzflj.readString();
                    continue;
                case 26:
                    this.zzgim = zzflj.readString();
                    continue;
                case 32:
                    this.zzjum = Long.valueOf(zzflj.zzcyr());
                    continue;
                case 45:
                    this.zzjsk = Float.valueOf(Float.intBitsToFloat(zzflj.zzcys()));
                    continue;
                case 49:
                    this.zzjsl = Double.valueOf(Double.longBitsToDouble(zzflj.zzcyt()));
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
        if (this.zzjvr != null) {
            zzflk.zzf(1, this.zzjvr.longValue());
        }
        if (this.name != null) {
            zzflk.zzp(2, this.name);
        }
        if (this.zzgim != null) {
            zzflk.zzp(3, this.zzgim);
        }
        if (this.zzjum != null) {
            zzflk.zzf(4, this.zzjum.longValue());
        }
        if (this.zzjsk != null) {
            zzflk.zzd(5, this.zzjsk.floatValue());
        }
        if (this.zzjsl != null) {
            zzflk.zza(6, this.zzjsl.doubleValue());
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (this.zzjvr != null) {
            zzq += zzflk.zzc(1, this.zzjvr.longValue());
        }
        if (this.name != null) {
            zzq += zzflk.zzq(2, this.name);
        }
        if (this.zzgim != null) {
            zzq += zzflk.zzq(3, this.zzgim);
        }
        if (this.zzjum != null) {
            zzq += zzflk.zzc(4, this.zzjum.longValue());
        }
        if (this.zzjsk != null) {
            this.zzjsk.floatValue();
            zzq += zzflk.zzlw(5) + 4;
        }
        if (this.zzjsl == null) {
            return zzq;
        }
        this.zzjsl.doubleValue();
        return zzq + (zzflk.zzlw(6) + 8);
    }
}
