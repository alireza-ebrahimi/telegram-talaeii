package com.google.android.gms.internal.measurement;

public class zzaaa {
    private static final zzzk zzbtg = zzzk.zztn();
    private zzyy zzbth;
    private volatile zzaan zzbti;
    private volatile zzyy zzbtj;

    private final zzaan zzb(zzaan zzaan) {
        if (this.zzbti == null) {
            synchronized (this) {
                if (this.zzbti != null) {
                } else {
                    try {
                        this.zzbti = zzaan;
                        this.zzbtj = zzyy.zzbrh;
                    } catch (zzzv e) {
                        this.zzbti = zzaan;
                        this.zzbtj = zzyy.zzbrh;
                    }
                }
            }
        }
        return this.zzbti;
    }

    private final zzyy zzty() {
        if (this.zzbtj != null) {
            return this.zzbtj;
        }
        synchronized (this) {
            if (this.zzbtj != null) {
                zzyy zzyy = this.zzbtj;
                return zzyy;
            }
            if (this.zzbti == null) {
                this.zzbtj = zzyy.zzbrh;
            } else {
                this.zzbtj = this.zzbti.zzty();
            }
            zzyy = this.zzbtj;
            return zzyy;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaaa)) {
            return false;
        }
        zzaaa zzaaa = (zzaaa) obj;
        zzaan zzaan = this.zzbti;
        zzaan zzaan2 = zzaaa.zzbti;
        return (zzaan == null && zzaan2 == null) ? zzty().equals(zzaaa.zzty()) : (zzaan == null || zzaan2 == null) ? zzaan != null ? zzaan.equals(zzaaa.zzb(zzaan.zzui())) : zzb(zzaan2.zzui()).equals(zzaan2) : zzaan.equals(zzaan2);
    }

    public int hashCode() {
        return 1;
    }

    public final zzaan zzc(zzaan zzaan) {
        zzaan zzaan2 = this.zzbti;
        this.zzbth = null;
        this.zzbtj = null;
        this.zzbti = zzaan;
        return zzaan2;
    }
}
