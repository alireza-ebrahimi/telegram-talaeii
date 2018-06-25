package com.google.android.gms.internal.measurement;

public final class zzkk extends zzaca<zzkk> {
    public Integer zzast;
    public String zzasu;
    public Boolean zzasv;
    public String[] zzasw;

    public zzkk() {
        this.zzast = null;
        this.zzasu = null;
        this.zzasv = null;
        this.zzasw = zzacj.zzbya;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    private final zzkk zze(zzabx zzabx) {
        int position;
        while (true) {
            int zzvf = zzabx.zzvf();
            switch (zzvf) {
                case 0:
                    break;
                case 8:
                    position = zzabx.getPosition();
                    try {
                        int zzvh = zzabx.zzvh();
                        if (zzvh < 0 || zzvh > 6) {
                            throw new IllegalArgumentException(zzvh + " is not a valid enum MatchType");
                        }
                        this.zzast = Integer.valueOf(zzvh);
                        continue;
                    } catch (IllegalArgumentException e) {
                        zzabx.zzam(position);
                        zza(zzabx, zzvf);
                        break;
                    }
                case 18:
                    this.zzasu = zzabx.readString();
                    continue;
                case 24:
                    this.zzasv = Boolean.valueOf(zzabx.zzvg());
                    continue;
                case 34:
                    position = zzacj.zzb(zzabx, 34);
                    zzvf = this.zzasw == null ? 0 : this.zzasw.length;
                    Object obj = new String[(position + zzvf)];
                    if (zzvf != 0) {
                        System.arraycopy(this.zzasw, 0, obj, 0, zzvf);
                    }
                    while (zzvf < obj.length - 1) {
                        obj[zzvf] = zzabx.readString();
                        zzabx.zzvf();
                        zzvf++;
                    }
                    obj[zzvf] = zzabx.readString();
                    this.zzasw = obj;
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
        if (!(obj instanceof zzkk)) {
            return false;
        }
        zzkk zzkk = (zzkk) obj;
        if (this.zzast == null) {
            if (zzkk.zzast != null) {
                return false;
            }
        } else if (!this.zzast.equals(zzkk.zzast)) {
            return false;
        }
        if (this.zzasu == null) {
            if (zzkk.zzasu != null) {
                return false;
            }
        } else if (!this.zzasu.equals(zzkk.zzasu)) {
            return false;
        }
        if (this.zzasv == null) {
            if (zzkk.zzasv != null) {
                return false;
            }
        } else if (!this.zzasv.equals(zzkk.zzasv)) {
            return false;
        }
        return !zzace.equals(this.zzasw, zzkk.zzasw) ? false : (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkk.zzbxg == null || zzkk.zzbxg.isEmpty() : this.zzbxg.equals(zzkk.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((this.zzasv == null ? 0 : this.zzasv.hashCode()) + (((this.zzasu == null ? 0 : this.zzasu.hashCode()) + (((this.zzast == null ? 0 : this.zzast.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31) + zzace.hashCode(this.zzasw)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int i = 0;
        int zza = super.zza();
        if (this.zzast != null) {
            zza += zzaby.zzf(1, this.zzast.intValue());
        }
        if (this.zzasu != null) {
            zza += zzaby.zzc(2, this.zzasu);
        }
        if (this.zzasv != null) {
            this.zzasv.booleanValue();
            zza += zzaby.zzaq(3) + 1;
        }
        if (this.zzasw == null || this.zzasw.length <= 0) {
            return zza;
        }
        int i2 = 0;
        int i3 = 0;
        while (i < this.zzasw.length) {
            String str = this.zzasw[i];
            if (str != null) {
                i3++;
                i2 += zzaby.zzfk(str);
            }
            i++;
        }
        return (zza + i2) + (i3 * 1);
    }

    public final void zza(zzaby zzaby) {
        if (this.zzast != null) {
            zzaby.zze(1, this.zzast.intValue());
        }
        if (this.zzasu != null) {
            zzaby.zzb(2, this.zzasu);
        }
        if (this.zzasv != null) {
            zzaby.zza(3, this.zzasv.booleanValue());
        }
        if (this.zzasw != null && this.zzasw.length > 0) {
            for (String str : this.zzasw) {
                if (str != null) {
                    zzaby.zzb(4, str);
                }
            }
        }
        super.zza(zzaby);
    }

    public final /* synthetic */ zzacg zzb(zzabx zzabx) {
        return zze(zzabx);
    }
}
