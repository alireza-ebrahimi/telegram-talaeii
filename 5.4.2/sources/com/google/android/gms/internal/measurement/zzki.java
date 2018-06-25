package com.google.android.gms.internal.measurement;

public final class zzki extends zzaca<zzki> {
    public Integer zzasl;
    public Boolean zzasm;
    public String zzasn;
    public String zzaso;
    public String zzasp;

    public zzki() {
        this.zzasl = null;
        this.zzasm = null;
        this.zzasn = null;
        this.zzaso = null;
        this.zzasp = null;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    private final zzki zzd(zzabx zzabx) {
        while (true) {
            int zzvf = zzabx.zzvf();
            switch (zzvf) {
                case 0:
                    break;
                case 8:
                    int position = zzabx.getPosition();
                    try {
                        int zzvh = zzabx.zzvh();
                        if (zzvh < 0 || zzvh > 4) {
                            throw new IllegalArgumentException(zzvh + " is not a valid enum ComparisonType");
                        }
                        this.zzasl = Integer.valueOf(zzvh);
                        continue;
                    } catch (IllegalArgumentException e) {
                        zzabx.zzam(position);
                        zza(zzabx, zzvf);
                        break;
                    }
                case 16:
                    this.zzasm = Boolean.valueOf(zzabx.zzvg());
                    continue;
                case 26:
                    this.zzasn = zzabx.readString();
                    continue;
                case 34:
                    this.zzaso = zzabx.readString();
                    continue;
                case 42:
                    this.zzasp = zzabx.readString();
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

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzki)) {
            return false;
        }
        zzki zzki = (zzki) obj;
        if (this.zzasl == null) {
            if (zzki.zzasl != null) {
                return false;
            }
        } else if (!this.zzasl.equals(zzki.zzasl)) {
            return false;
        }
        if (this.zzasm == null) {
            if (zzki.zzasm != null) {
                return false;
            }
        } else if (!this.zzasm.equals(zzki.zzasm)) {
            return false;
        }
        if (this.zzasn == null) {
            if (zzki.zzasn != null) {
                return false;
            }
        } else if (!this.zzasn.equals(zzki.zzasn)) {
            return false;
        }
        if (this.zzaso == null) {
            if (zzki.zzaso != null) {
                return false;
            }
        } else if (!this.zzaso.equals(zzki.zzaso)) {
            return false;
        }
        if (this.zzasp == null) {
            if (zzki.zzasp != null) {
                return false;
            }
        } else if (!this.zzasp.equals(zzki.zzasp)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzki.zzbxg == null || zzki.zzbxg.isEmpty() : this.zzbxg.equals(zzki.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzasp == null ? 0 : this.zzasp.hashCode()) + (((this.zzaso == null ? 0 : this.zzaso.hashCode()) + (((this.zzasn == null ? 0 : this.zzasn.hashCode()) + (((this.zzasm == null ? 0 : this.zzasm.hashCode()) + (((this.zzasl == null ? 0 : this.zzasl.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zzasl != null) {
            zza += zzaby.zzf(1, this.zzasl.intValue());
        }
        if (this.zzasm != null) {
            this.zzasm.booleanValue();
            zza += zzaby.zzaq(2) + 1;
        }
        if (this.zzasn != null) {
            zza += zzaby.zzc(3, this.zzasn);
        }
        if (this.zzaso != null) {
            zza += zzaby.zzc(4, this.zzaso);
        }
        return this.zzasp != null ? zza + zzaby.zzc(5, this.zzasp) : zza;
    }

    public final void zza(zzaby zzaby) {
        if (this.zzasl != null) {
            zzaby.zze(1, this.zzasl.intValue());
        }
        if (this.zzasm != null) {
            zzaby.zza(2, this.zzasm.booleanValue());
        }
        if (this.zzasn != null) {
            zzaby.zzb(3, this.zzasn);
        }
        if (this.zzaso != null) {
            zzaby.zzb(4, this.zzaso);
        }
        if (this.zzasp != null) {
            zzaby.zzb(5, this.zzasp);
        }
        super.zza(zzaby);
    }

    public final /* synthetic */ zzacg zzb(zzabx zzabx) {
        return zzd(zzabx);
    }
}
