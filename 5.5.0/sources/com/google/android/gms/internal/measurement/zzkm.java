package com.google.android.gms.internal.measurement;

public final class zzkm extends zzaca<zzkm> {
    public String zzadm;
    public Long zzatb;
    private Integer zzatc;
    public zzkn[] zzatd;
    public zzkl[] zzate;
    public zzkf[] zzatf;

    public zzkm() {
        this.zzatb = null;
        this.zzadm = null;
        this.zzatc = null;
        this.zzatd = zzkn.zzls();
        this.zzate = zzkl.zzlr();
        this.zzatf = zzkf.zzln();
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkm)) {
            return false;
        }
        zzkm zzkm = (zzkm) obj;
        if (this.zzatb == null) {
            if (zzkm.zzatb != null) {
                return false;
            }
        } else if (!this.zzatb.equals(zzkm.zzatb)) {
            return false;
        }
        if (this.zzadm == null) {
            if (zzkm.zzadm != null) {
                return false;
            }
        } else if (!this.zzadm.equals(zzkm.zzadm)) {
            return false;
        }
        if (this.zzatc == null) {
            if (zzkm.zzatc != null) {
                return false;
            }
        } else if (!this.zzatc.equals(zzkm.zzatc)) {
            return false;
        }
        return !zzace.equals(this.zzatd, zzkm.zzatd) ? false : !zzace.equals(this.zzate, zzkm.zzate) ? false : !zzace.equals(this.zzatf, zzkm.zzatf) ? false : (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkm.zzbxg == null || zzkm.zzbxg.isEmpty() : this.zzbxg.equals(zzkm.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((this.zzatc == null ? 0 : this.zzatc.hashCode()) + (((this.zzadm == null ? 0 : this.zzadm.hashCode()) + (((this.zzatb == null ? 0 : this.zzatb.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31) + zzace.hashCode(this.zzatd)) * 31) + zzace.hashCode(this.zzate)) * 31) + zzace.hashCode(this.zzatf)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int i;
        int i2 = 0;
        int zza = super.zza();
        if (this.zzatb != null) {
            zza += zzaby.zzc(1, this.zzatb.longValue());
        }
        if (this.zzadm != null) {
            zza += zzaby.zzc(2, this.zzadm);
        }
        if (this.zzatc != null) {
            zza += zzaby.zzf(3, this.zzatc.intValue());
        }
        if (this.zzatd != null && this.zzatd.length > 0) {
            i = zza;
            for (zzacg zzacg : this.zzatd) {
                if (zzacg != null) {
                    i += zzaby.zzb(4, zzacg);
                }
            }
            zza = i;
        }
        if (this.zzate != null && this.zzate.length > 0) {
            i = zza;
            for (zzacg zzacg2 : this.zzate) {
                if (zzacg2 != null) {
                    i += zzaby.zzb(5, zzacg2);
                }
            }
            zza = i;
        }
        if (this.zzatf != null && this.zzatf.length > 0) {
            while (i2 < this.zzatf.length) {
                zzacg zzacg3 = this.zzatf[i2];
                if (zzacg3 != null) {
                    zza += zzaby.zzb(6, zzacg3);
                }
                i2++;
            }
        }
        return zza;
    }

    public final void zza(zzaby zzaby) {
        int i = 0;
        if (this.zzatb != null) {
            zzaby.zzb(1, this.zzatb.longValue());
        }
        if (this.zzadm != null) {
            zzaby.zzb(2, this.zzadm);
        }
        if (this.zzatc != null) {
            zzaby.zze(3, this.zzatc.intValue());
        }
        if (this.zzatd != null && this.zzatd.length > 0) {
            for (zzacg zzacg : this.zzatd) {
                if (zzacg != null) {
                    zzaby.zza(4, zzacg);
                }
            }
        }
        if (this.zzate != null && this.zzate.length > 0) {
            for (zzacg zzacg2 : this.zzate) {
                if (zzacg2 != null) {
                    zzaby.zza(5, zzacg2);
                }
            }
        }
        if (this.zzatf != null && this.zzatf.length > 0) {
            while (i < this.zzatf.length) {
                zzacg zzacg3 = this.zzatf[i];
                if (zzacg3 != null) {
                    zzaby.zza(6, zzacg3);
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
                    this.zzatb = Long.valueOf(zzabx.zzvi());
                    continue;
                case 18:
                    this.zzadm = zzabx.readString();
                    continue;
                case 24:
                    this.zzatc = Integer.valueOf(zzabx.zzvh());
                    continue;
                case 34:
                    zzb = zzacj.zzb(zzabx, 34);
                    zzvf = this.zzatd == null ? 0 : this.zzatd.length;
                    obj = new zzkn[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzatd, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = new zzkn();
                        zzabx.zza(obj[zzvf]);
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = new zzkn();
                    zzabx.zza(obj[zzvf]);
                    this.zzatd = obj;
                    continue;
                case 42:
                    zzb = zzacj.zzb(zzabx, 42);
                    zzvf = this.zzate == null ? 0 : this.zzate.length;
                    obj = new zzkl[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzate, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = new zzkl();
                        zzabx.zza(obj[zzvf]);
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = new zzkl();
                    zzabx.zza(obj[zzvf]);
                    this.zzate = obj;
                    continue;
                case 50:
                    zzb = zzacj.zzb(zzabx, 50);
                    zzvf = this.zzatf == null ? 0 : this.zzatf.length;
                    obj = new zzkf[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzatf, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = new zzkf();
                        zzabx.zza(obj[zzvf]);
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = new zzkf();
                    zzabx.zza(obj[zzvf]);
                    this.zzatf = obj;
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
