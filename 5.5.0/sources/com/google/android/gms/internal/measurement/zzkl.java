package com.google.android.gms.internal.measurement;

public final class zzkl extends zzaca<zzkl> {
    private static volatile zzkl[] zzasx;
    public String name;
    public Boolean zzasy;
    public Boolean zzasz;
    public Integer zzata;

    public zzkl() {
        this.name = null;
        this.zzasy = null;
        this.zzasz = null;
        this.zzata = null;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkl[] zzlr() {
        if (zzasx == null) {
            synchronized (zzace.zzbxq) {
                if (zzasx == null) {
                    zzasx = new zzkl[0];
                }
            }
        }
        return zzasx;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkl)) {
            return false;
        }
        zzkl zzkl = (zzkl) obj;
        if (this.name == null) {
            if (zzkl.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzkl.name)) {
            return false;
        }
        if (this.zzasy == null) {
            if (zzkl.zzasy != null) {
                return false;
            }
        } else if (!this.zzasy.equals(zzkl.zzasy)) {
            return false;
        }
        if (this.zzasz == null) {
            if (zzkl.zzasz != null) {
                return false;
            }
        } else if (!this.zzasz.equals(zzkl.zzasz)) {
            return false;
        }
        if (this.zzata == null) {
            if (zzkl.zzata != null) {
                return false;
            }
        } else if (!this.zzata.equals(zzkl.zzata)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkl.zzbxg == null || zzkl.zzbxg.isEmpty() : this.zzbxg.equals(zzkl.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzata == null ? 0 : this.zzata.hashCode()) + (((this.zzasz == null ? 0 : this.zzasz.hashCode()) + (((this.zzasy == null ? 0 : this.zzasy.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.name != null) {
            zza += zzaby.zzc(1, this.name);
        }
        if (this.zzasy != null) {
            this.zzasy.booleanValue();
            zza += zzaby.zzaq(2) + 1;
        }
        if (this.zzasz != null) {
            this.zzasz.booleanValue();
            zza += zzaby.zzaq(3) + 1;
        }
        return this.zzata != null ? zza + zzaby.zzf(4, this.zzata.intValue()) : zza;
    }

    public final void zza(zzaby zzaby) {
        if (this.name != null) {
            zzaby.zzb(1, this.name);
        }
        if (this.zzasy != null) {
            zzaby.zza(2, this.zzasy.booleanValue());
        }
        if (this.zzasz != null) {
            zzaby.zza(3, this.zzasz.booleanValue());
        }
        if (this.zzata != null) {
            zzaby.zze(4, this.zzata.intValue());
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
                    this.name = zzabx.readString();
                    continue;
                case 16:
                    this.zzasy = Boolean.valueOf(zzabx.zzvg());
                    continue;
                case 24:
                    this.zzasz = Boolean.valueOf(zzabx.zzvg());
                    continue;
                case 32:
                    this.zzata = Integer.valueOf(zzabx.zzvh());
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
