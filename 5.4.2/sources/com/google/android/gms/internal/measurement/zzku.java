package com.google.android.gms.internal.measurement;

public final class zzku extends zzaca<zzku> {
    private static volatile zzku[] zzauy;
    public String name;
    public String zzajo;
    private Float zzarn;
    public Double zzaro;
    public Long zzatq;
    public Long zzauz;

    public zzku() {
        this.zzauz = null;
        this.name = null;
        this.zzajo = null;
        this.zzatq = null;
        this.zzarn = null;
        this.zzaro = null;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzku[] zzlx() {
        if (zzauy == null) {
            synchronized (zzace.zzbxq) {
                if (zzauy == null) {
                    zzauy = new zzku[0];
                }
            }
        }
        return zzauy;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzku)) {
            return false;
        }
        zzku zzku = (zzku) obj;
        if (this.zzauz == null) {
            if (zzku.zzauz != null) {
                return false;
            }
        } else if (!this.zzauz.equals(zzku.zzauz)) {
            return false;
        }
        if (this.name == null) {
            if (zzku.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzku.name)) {
            return false;
        }
        if (this.zzajo == null) {
            if (zzku.zzajo != null) {
                return false;
            }
        } else if (!this.zzajo.equals(zzku.zzajo)) {
            return false;
        }
        if (this.zzatq == null) {
            if (zzku.zzatq != null) {
                return false;
            }
        } else if (!this.zzatq.equals(zzku.zzatq)) {
            return false;
        }
        if (this.zzarn == null) {
            if (zzku.zzarn != null) {
                return false;
            }
        } else if (!this.zzarn.equals(zzku.zzarn)) {
            return false;
        }
        if (this.zzaro == null) {
            if (zzku.zzaro != null) {
                return false;
            }
        } else if (!this.zzaro.equals(zzku.zzaro)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzku.zzbxg == null || zzku.zzbxg.isEmpty() : this.zzbxg.equals(zzku.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzaro == null ? 0 : this.zzaro.hashCode()) + (((this.zzarn == null ? 0 : this.zzarn.hashCode()) + (((this.zzatq == null ? 0 : this.zzatq.hashCode()) + (((this.zzajo == null ? 0 : this.zzajo.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + (((this.zzauz == null ? 0 : this.zzauz.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zzauz != null) {
            zza += zzaby.zzc(1, this.zzauz.longValue());
        }
        if (this.name != null) {
            zza += zzaby.zzc(2, this.name);
        }
        if (this.zzajo != null) {
            zza += zzaby.zzc(3, this.zzajo);
        }
        if (this.zzatq != null) {
            zza += zzaby.zzc(4, this.zzatq.longValue());
        }
        if (this.zzarn != null) {
            this.zzarn.floatValue();
            zza += zzaby.zzaq(5) + 4;
        }
        if (this.zzaro == null) {
            return zza;
        }
        this.zzaro.doubleValue();
        return zza + (zzaby.zzaq(6) + 8);
    }

    public final void zza(zzaby zzaby) {
        if (this.zzauz != null) {
            zzaby.zzb(1, this.zzauz.longValue());
        }
        if (this.name != null) {
            zzaby.zzb(2, this.name);
        }
        if (this.zzajo != null) {
            zzaby.zzb(3, this.zzajo);
        }
        if (this.zzatq != null) {
            zzaby.zzb(4, this.zzatq.longValue());
        }
        if (this.zzarn != null) {
            zzaby.zza(5, this.zzarn.floatValue());
        }
        if (this.zzaro != null) {
            zzaby.zza(6, this.zzaro.doubleValue());
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
                    this.zzauz = Long.valueOf(zzabx.zzvi());
                    continue;
                case 18:
                    this.name = zzabx.readString();
                    continue;
                case 26:
                    this.zzajo = zzabx.readString();
                    continue;
                case 32:
                    this.zzatq = Long.valueOf(zzabx.zzvi());
                    continue;
                case 45:
                    this.zzarn = Float.valueOf(Float.intBitsToFloat(zzabx.zzvj()));
                    continue;
                case 49:
                    this.zzaro = Double.valueOf(Double.longBitsToDouble(zzabx.zzvk()));
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
