package com.google.android.gms.internal.measurement;

public final class zzkh extends zzaca<zzkh> {
    private static volatile zzkh[] zzasg;
    public zzkk zzash;
    public zzki zzasi;
    public Boolean zzasj;
    public String zzask;

    public zzkh() {
        this.zzash = null;
        this.zzasi = null;
        this.zzasj = null;
        this.zzask = null;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkh[] zzlp() {
        if (zzasg == null) {
            synchronized (zzace.zzbxq) {
                if (zzasg == null) {
                    zzasg = new zzkh[0];
                }
            }
        }
        return zzasg;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkh)) {
            return false;
        }
        zzkh zzkh = (zzkh) obj;
        if (this.zzash == null) {
            if (zzkh.zzash != null) {
                return false;
            }
        } else if (!this.zzash.equals(zzkh.zzash)) {
            return false;
        }
        if (this.zzasi == null) {
            if (zzkh.zzasi != null) {
                return false;
            }
        } else if (!this.zzasi.equals(zzkh.zzasi)) {
            return false;
        }
        if (this.zzasj == null) {
            if (zzkh.zzasj != null) {
                return false;
            }
        } else if (!this.zzasj.equals(zzkh.zzasj)) {
            return false;
        }
        if (this.zzask == null) {
            if (zzkh.zzask != null) {
                return false;
            }
        } else if (!this.zzask.equals(zzkh.zzask)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkh.zzbxg == null || zzkh.zzbxg.isEmpty() : this.zzbxg.equals(zzkh.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = getClass().getName().hashCode() + 527;
        zzkk zzkk = this.zzash;
        hashCode = (zzkk == null ? 0 : zzkk.hashCode()) + (hashCode * 31);
        zzki zzki = this.zzasi;
        hashCode = ((this.zzask == null ? 0 : this.zzask.hashCode()) + (((this.zzasj == null ? 0 : this.zzasj.hashCode()) + (((zzki == null ? 0 : zzki.hashCode()) + (hashCode * 31)) * 31)) * 31)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zzash != null) {
            zza += zzaby.zzb(1, this.zzash);
        }
        if (this.zzasi != null) {
            zza += zzaby.zzb(2, this.zzasi);
        }
        if (this.zzasj != null) {
            this.zzasj.booleanValue();
            zza += zzaby.zzaq(3) + 1;
        }
        return this.zzask != null ? zza + zzaby.zzc(4, this.zzask) : zza;
    }

    public final void zza(zzaby zzaby) {
        if (this.zzash != null) {
            zzaby.zza(1, this.zzash);
        }
        if (this.zzasi != null) {
            zzaby.zza(2, this.zzasi);
        }
        if (this.zzasj != null) {
            zzaby.zza(3, this.zzasj.booleanValue());
        }
        if (this.zzask != null) {
            zzaby.zzb(4, this.zzask);
        }
        super.zza(zzaby);
    }

    public final /* synthetic */ zzacg zzb(zzabx zzabx) {
        while (true) {
            int zzvf = zzabx.zzvf();
            switch (zzvf) {
                case 0:
                    break;
                case 10:
                    if (this.zzash == null) {
                        this.zzash = new zzkk();
                    }
                    zzabx.zza(this.zzash);
                    continue;
                case 18:
                    if (this.zzasi == null) {
                        this.zzasi = new zzki();
                    }
                    zzabx.zza(this.zzasi);
                    continue;
                case 24:
                    this.zzasj = Boolean.valueOf(zzabx.zzvg());
                    continue;
                case 34:
                    this.zzask = zzabx.readString();
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
