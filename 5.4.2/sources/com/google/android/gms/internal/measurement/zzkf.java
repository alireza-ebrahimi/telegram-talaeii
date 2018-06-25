package com.google.android.gms.internal.measurement;

public final class zzkf extends zzaca<zzkf> {
    private static volatile zzkf[] zzarw;
    public Integer zzarx;
    public zzkj[] zzary;
    public zzkg[] zzarz;

    public zzkf() {
        this.zzarx = null;
        this.zzary = zzkj.zzlq();
        this.zzarz = zzkg.zzlo();
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkf[] zzln() {
        if (zzarw == null) {
            synchronized (zzace.zzbxq) {
                if (zzarw == null) {
                    zzarw = new zzkf[0];
                }
            }
        }
        return zzarw;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkf)) {
            return false;
        }
        zzkf zzkf = (zzkf) obj;
        if (this.zzarx == null) {
            if (zzkf.zzarx != null) {
                return false;
            }
        } else if (!this.zzarx.equals(zzkf.zzarx)) {
            return false;
        }
        return !zzace.equals(this.zzary, zzkf.zzary) ? false : !zzace.equals(this.zzarz, zzkf.zzarz) ? false : (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkf.zzbxg == null || zzkf.zzbxg.isEmpty() : this.zzbxg.equals(zzkf.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((this.zzarx == null ? 0 : this.zzarx.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + zzace.hashCode(this.zzary)) * 31) + zzace.hashCode(this.zzarz)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int i = 0;
        int zza = super.zza();
        if (this.zzarx != null) {
            zza += zzaby.zzf(1, this.zzarx.intValue());
        }
        if (this.zzary != null && this.zzary.length > 0) {
            int i2 = zza;
            for (zzacg zzacg : this.zzary) {
                if (zzacg != null) {
                    i2 += zzaby.zzb(2, zzacg);
                }
            }
            zza = i2;
        }
        if (this.zzarz != null && this.zzarz.length > 0) {
            while (i < this.zzarz.length) {
                zzacg zzacg2 = this.zzarz[i];
                if (zzacg2 != null) {
                    zza += zzaby.zzb(3, zzacg2);
                }
                i++;
            }
        }
        return zza;
    }

    public final void zza(zzaby zzaby) {
        int i = 0;
        if (this.zzarx != null) {
            zzaby.zze(1, this.zzarx.intValue());
        }
        if (this.zzary != null && this.zzary.length > 0) {
            for (zzacg zzacg : this.zzary) {
                if (zzacg != null) {
                    zzaby.zza(2, zzacg);
                }
            }
        }
        if (this.zzarz != null && this.zzarz.length > 0) {
            while (i < this.zzarz.length) {
                zzacg zzacg2 = this.zzarz[i];
                if (zzacg2 != null) {
                    zzaby.zza(3, zzacg2);
                }
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
            switch (zzvf) {
                case 0:
                    break;
                case 8:
                    this.zzarx = Integer.valueOf(zzabx.zzvh());
                    continue;
                case 18:
                    zzb = zzacj.zzb(zzabx, 18);
                    zzvf = this.zzary == null ? 0 : this.zzary.length;
                    obj = new zzkj[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzary, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = new zzkj();
                        zzabx.zza(obj[zzvf]);
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = new zzkj();
                    zzabx.zza(obj[zzvf]);
                    this.zzary = obj;
                    continue;
                case 26:
                    zzb = zzacj.zzb(zzabx, 26);
                    zzvf = this.zzarz == null ? 0 : this.zzarz.length;
                    obj = new zzkg[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzarz, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = new zzkg();
                        zzabx.zza(obj[zzvf]);
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = new zzkg();
                    zzabx.zza(obj[zzvf]);
                    this.zzarz = obj;
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
