package com.google.android.gms.internal.measurement;

public final class zzkr extends zzaca<zzkr> {
    public zzks[] zzatr;

    public zzkr() {
        this.zzatr = zzks.zzlw();
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkr)) {
            return false;
        }
        zzkr zzkr = (zzkr) obj;
        return !zzace.equals(this.zzatr, zzkr.zzatr) ? false : (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkr.zzbxg == null || zzkr.zzbxg.isEmpty() : this.zzbxg.equals(zzkr.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + 527) * 31) + zzace.hashCode(this.zzatr)) * 31;
        int hashCode2 = (this.zzbxg == null || this.zzbxg.isEmpty()) ? 0 : this.zzbxg.hashCode();
        return hashCode2 + hashCode;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zzatr != null && this.zzatr.length > 0) {
            for (zzacg zzacg : this.zzatr) {
                if (zzacg != null) {
                    zza += zzaby.zzb(1, zzacg);
                }
            }
        }
        return zza;
    }

    public final void zza(zzaby zzaby) {
        if (this.zzatr != null && this.zzatr.length > 0) {
            for (zzacg zzacg : this.zzatr) {
                if (zzacg != null) {
                    zzaby.zza(1, zzacg);
                }
            }
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
                    int zzb = zzacj.zzb(zzabx, 10);
                    zzvf = this.zzatr == null ? 0 : this.zzatr.length;
                    Object obj = new zzks[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzatr, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = new zzks();
                        zzabx.zza(obj[zzvf]);
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = new zzks();
                    zzabx.zza(obj[zzvf]);
                    this.zzatr = obj;
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
