package com.google.android.gms.internal.measurement;

public final class zzkj extends zzaca<zzkj> {
    private static volatile zzkj[] zzasq;
    public Integer zzasb;
    public String zzasr;
    public zzkh zzass;

    public zzkj() {
        this.zzasb = null;
        this.zzasr = null;
        this.zzass = null;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkj[] zzlq() {
        if (zzasq == null) {
            synchronized (zzace.zzbxq) {
                if (zzasq == null) {
                    zzasq = new zzkj[0];
                }
            }
        }
        return zzasq;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkj)) {
            return false;
        }
        zzkj zzkj = (zzkj) obj;
        if (this.zzasb == null) {
            if (zzkj.zzasb != null) {
                return false;
            }
        } else if (!this.zzasb.equals(zzkj.zzasb)) {
            return false;
        }
        if (this.zzasr == null) {
            if (zzkj.zzasr != null) {
                return false;
            }
        } else if (!this.zzasr.equals(zzkj.zzasr)) {
            return false;
        }
        if (this.zzass == null) {
            if (zzkj.zzass != null) {
                return false;
            }
        } else if (!this.zzass.equals(zzkj.zzass)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkj.zzbxg == null || zzkj.zzbxg.isEmpty() : this.zzbxg.equals(zzkj.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.zzasr == null ? 0 : this.zzasr.hashCode()) + (((this.zzasb == null ? 0 : this.zzasb.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31);
        zzkh zzkh = this.zzass;
        hashCode = ((zzkh == null ? 0 : zzkh.hashCode()) + (hashCode * 31)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zzasb != null) {
            zza += zzaby.zzf(1, this.zzasb.intValue());
        }
        if (this.zzasr != null) {
            zza += zzaby.zzc(2, this.zzasr);
        }
        return this.zzass != null ? zza + zzaby.zzb(3, this.zzass) : zza;
    }

    public final void zza(zzaby zzaby) {
        if (this.zzasb != null) {
            zzaby.zze(1, this.zzasb.intValue());
        }
        if (this.zzasr != null) {
            zzaby.zzb(2, this.zzasr);
        }
        if (this.zzass != null) {
            zzaby.zza(3, this.zzass);
        }
        super.zza(zzaby);
    }

    public final /* synthetic */ zzacg zzb(zzabx zzabx) {
        while (true) {
            int zzvf = zzabx.zzvf();
            switch (zzvf) {
                case 0:
                    break;
                case 8:
                    this.zzasb = Integer.valueOf(zzabx.zzvh());
                    continue;
                case 18:
                    this.zzasr = zzabx.readString();
                    continue;
                case 26:
                    if (this.zzass == null) {
                        this.zzass = new zzkh();
                    }
                    zzabx.zza(this.zzass);
                    continue;
                default:
                    if (!super.zza(zzabx, zzvf)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }
}
