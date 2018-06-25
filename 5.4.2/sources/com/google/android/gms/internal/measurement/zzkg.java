package com.google.android.gms.internal.measurement;

public final class zzkg extends zzaca<zzkg> {
    private static volatile zzkg[] zzasa;
    public Integer zzasb;
    public String zzasc;
    public zzkh[] zzasd;
    private Boolean zzase;
    public zzki zzasf;

    public zzkg() {
        this.zzasb = null;
        this.zzasc = null;
        this.zzasd = zzkh.zzlp();
        this.zzase = null;
        this.zzasf = null;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkg[] zzlo() {
        if (zzasa == null) {
            synchronized (zzace.zzbxq) {
                if (zzasa == null) {
                    zzasa = new zzkg[0];
                }
            }
        }
        return zzasa;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkg)) {
            return false;
        }
        zzkg zzkg = (zzkg) obj;
        if (this.zzasb == null) {
            if (zzkg.zzasb != null) {
                return false;
            }
        } else if (!this.zzasb.equals(zzkg.zzasb)) {
            return false;
        }
        if (this.zzasc == null) {
            if (zzkg.zzasc != null) {
                return false;
            }
        } else if (!this.zzasc.equals(zzkg.zzasc)) {
            return false;
        }
        if (!zzace.equals(this.zzasd, zzkg.zzasd)) {
            return false;
        }
        if (this.zzase == null) {
            if (zzkg.zzase != null) {
                return false;
            }
        } else if (!this.zzase.equals(zzkg.zzase)) {
            return false;
        }
        if (this.zzasf == null) {
            if (zzkg.zzasf != null) {
                return false;
            }
        } else if (!this.zzasf.equals(zzkg.zzasf)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkg.zzbxg == null || zzkg.zzbxg.isEmpty() : this.zzbxg.equals(zzkg.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.zzase == null ? 0 : this.zzase.hashCode()) + (((((this.zzasc == null ? 0 : this.zzasc.hashCode()) + (((this.zzasb == null ? 0 : this.zzasb.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + zzace.hashCode(this.zzasd)) * 31);
        zzki zzki = this.zzasf;
        hashCode = ((zzki == null ? 0 : zzki.hashCode()) + (hashCode * 31)) * 31;
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
        if (this.zzasc != null) {
            zza += zzaby.zzc(2, this.zzasc);
        }
        if (this.zzasd != null && this.zzasd.length > 0) {
            int i = zza;
            for (zzacg zzacg : this.zzasd) {
                if (zzacg != null) {
                    i += zzaby.zzb(3, zzacg);
                }
            }
            zza = i;
        }
        if (this.zzase != null) {
            this.zzase.booleanValue();
            zza += zzaby.zzaq(4) + 1;
        }
        return this.zzasf != null ? zza + zzaby.zzb(5, this.zzasf) : zza;
    }

    public final void zza(zzaby zzaby) {
        if (this.zzasb != null) {
            zzaby.zze(1, this.zzasb.intValue());
        }
        if (this.zzasc != null) {
            zzaby.zzb(2, this.zzasc);
        }
        if (this.zzasd != null && this.zzasd.length > 0) {
            for (zzacg zzacg : this.zzasd) {
                if (zzacg != null) {
                    zzaby.zza(3, zzacg);
                }
            }
        }
        if (this.zzase != null) {
            zzaby.zza(4, this.zzase.booleanValue());
        }
        if (this.zzasf != null) {
            zzaby.zza(5, this.zzasf);
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
                    this.zzasc = zzabx.readString();
                    continue;
                case 26:
                    int zzb = zzacj.zzb(zzabx, 26);
                    zzvf = this.zzasd == null ? 0 : this.zzasd.length;
                    Object obj = new zzkh[(zzb + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzasd, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = new zzkh();
                        zzabx.zza(obj[zzvf]);
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = new zzkh();
                    zzabx.zza(obj[zzvf]);
                    this.zzasd = obj;
                    continue;
                case 32:
                    this.zzase = Boolean.valueOf(zzabx.zzvg());
                    continue;
                case 42:
                    if (this.zzasf == null) {
                        this.zzasf = new zzki();
                    }
                    zzabx.zza(this.zzasf);
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
