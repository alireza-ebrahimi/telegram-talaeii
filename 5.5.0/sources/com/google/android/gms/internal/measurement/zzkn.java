package com.google.android.gms.internal.measurement;

public final class zzkn extends zzaca<zzkn> {
    private static volatile zzkn[] zzatg;
    public String value;
    public String zzny;

    public zzkn() {
        this.zzny = null;
        this.value = null;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkn[] zzls() {
        if (zzatg == null) {
            synchronized (zzace.zzbxq) {
                if (zzatg == null) {
                    zzatg = new zzkn[0];
                }
            }
        }
        return zzatg;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkn)) {
            return false;
        }
        zzkn zzkn = (zzkn) obj;
        if (this.zzny == null) {
            if (zzkn.zzny != null) {
                return false;
            }
        } else if (!this.zzny.equals(zzkn.zzny)) {
            return false;
        }
        if (this.value == null) {
            if (zzkn.value != null) {
                return false;
            }
        } else if (!this.value.equals(zzkn.value)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkn.zzbxg == null || zzkn.zzbxg.isEmpty() : this.zzbxg.equals(zzkn.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.value == null ? 0 : this.value.hashCode()) + (((this.zzny == null ? 0 : this.zzny.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zzny != null) {
            zza += zzaby.zzc(1, this.zzny);
        }
        return this.value != null ? zza + zzaby.zzc(2, this.value) : zza;
    }

    public final void zza(zzaby zzaby) {
        if (this.zzny != null) {
            zzaby.zzb(1, this.zzny);
        }
        if (this.value != null) {
            zzaby.zzb(2, this.value);
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
                    this.zzny = zzabx.readString();
                    continue;
                case 18:
                    this.value = zzabx.readString();
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
