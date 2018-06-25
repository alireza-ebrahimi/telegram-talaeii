package com.google.android.gms.internal.measurement;

public final class zzkt extends zzaca<zzkt> {
    public long[] zzauw;
    public long[] zzaux;

    public zzkt() {
        this.zzauw = zzacj.zzbxw;
        this.zzaux = zzacj.zzbxw;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkt)) {
            return false;
        }
        zzkt zzkt = (zzkt) obj;
        return !zzace.equals(this.zzauw, zzkt.zzauw) ? false : !zzace.equals(this.zzaux, zzkt.zzaux) ? false : (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkt.zzbxg == null || zzkt.zzbxg.isEmpty() : this.zzbxg.equals(zzkt.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (((((getClass().getName().hashCode() + 527) * 31) + zzace.hashCode(this.zzauw)) * 31) + zzace.hashCode(this.zzaux)) * 31;
        int hashCode2 = (this.zzbxg == null || this.zzbxg.isEmpty()) ? 0 : this.zzbxg.hashCode();
        return hashCode2 + hashCode;
    }

    protected final int zza() {
        int i;
        int i2;
        int i3 = 0;
        int zza = super.zza();
        if (this.zzauw == null || this.zzauw.length <= 0) {
            i = zza;
        } else {
            i2 = 0;
            for (long zzao : this.zzauw) {
                i2 += zzaby.zzao(zzao);
            }
            i = (zza + i2) + (this.zzauw.length * 1);
        }
        if (this.zzaux == null || this.zzaux.length <= 0) {
            return i;
        }
        i2 = 0;
        while (i3 < this.zzaux.length) {
            i2 += zzaby.zzao(this.zzaux[i3]);
            i3++;
        }
        return (i + i2) + (this.zzaux.length * 1);
    }

    public final void zza(zzaby zzaby) {
        int i = 0;
        if (this.zzauw != null && this.zzauw.length > 0) {
            for (long zza : this.zzauw) {
                zzaby.zza(1, zza);
            }
        }
        if (this.zzaux != null && this.zzaux.length > 0) {
            while (i < this.zzaux.length) {
                zzaby.zza(2, this.zzaux[i]);
                i++;
            }
        }
        super.zza(zzaby);
    }

    public final /* synthetic */ zzacg zzb(zzabx zzabx) {
        while (true) {
            int zzvf = zzabx.zzvf();
            int zzb;
            Object obj;
            int zzaf;
            Object obj2;
            switch (zzvf) {
                case 0:
                    break;
                case 8:
                    zzb = zzacj.zzb(zzabx, 8);
                    zzvf = this.zzauw == null ? 0 : this.zzauw.length;
                    obj = new long[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzauw, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = zzabx.zzvi();
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = zzabx.zzvi();
                    this.zzauw = obj;
                    continue;
                case 10:
                    zzaf = zzabx.zzaf(zzabx.zzvh());
                    zzb = zzabx.getPosition();
                    zzvf = 0;
                    while (zzabx.zzvl() > 0) {
                        zzabx.zzvi();
                        zzvf++;
                    }
                    zzabx.zzam(zzb);
                    zzb = this.zzauw == null ? 0 : this.zzauw.length;
                    obj2 = new long[(zzvf + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzauw, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = zzabx.zzvi();
                        zzb++;
                    }
                    this.zzauw = obj2;
                    zzabx.zzal(zzaf);
                    continue;
                case 16:
                    zzb = zzacj.zzb(zzabx, 16);
                    zzvf = this.zzaux == null ? 0 : this.zzaux.length;
                    obj = new long[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzaux, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = zzabx.zzvi();
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = zzabx.zzvi();
                    this.zzaux = obj;
                    continue;
                case 18:
                    zzaf = zzabx.zzaf(zzabx.zzvh());
                    zzb = zzabx.getPosition();
                    zzvf = 0;
                    while (zzabx.zzvl() > 0) {
                        zzabx.zzvi();
                        zzvf++;
                    }
                    zzabx.zzam(zzb);
                    zzb = this.zzaux == null ? 0 : this.zzaux.length;
                    obj2 = new long[(zzvf + zzb)];
                    if (zzb != 0) {
                        System.arraycopy(this.zzaux, 0, obj2, 0, zzb);
                    }
                    while (zzb < obj2.length) {
                        obj2[zzb] = zzabx.zzvi();
                        zzb++;
                    }
                    this.zzaux = obj2;
                    zzabx.zzal(zzaf);
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
