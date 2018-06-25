package com.google.android.gms.internal.measurement;

public final class zzko extends zzaca<zzko> {
    private static volatile zzko[] zzath;
    public Integer zzarx;
    public zzkt zzati;
    public zzkt zzatj;
    public Boolean zzatk;

    public zzko() {
        this.zzarx = null;
        this.zzati = null;
        this.zzatj = null;
        this.zzatk = null;
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzko[] zzlt() {
        if (zzath == null) {
            synchronized (zzace.zzbxq) {
                if (zzath == null) {
                    zzath = new zzko[0];
                }
            }
        }
        return zzath;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzko)) {
            return false;
        }
        zzko zzko = (zzko) obj;
        if (this.zzarx == null) {
            if (zzko.zzarx != null) {
                return false;
            }
        } else if (!this.zzarx.equals(zzko.zzarx)) {
            return false;
        }
        if (this.zzati == null) {
            if (zzko.zzati != null) {
                return false;
            }
        } else if (!this.zzati.equals(zzko.zzati)) {
            return false;
        }
        if (this.zzatj == null) {
            if (zzko.zzatj != null) {
                return false;
            }
        } else if (!this.zzatj.equals(zzko.zzatj)) {
            return false;
        }
        if (this.zzatk == null) {
            if (zzko.zzatk != null) {
                return false;
            }
        } else if (!this.zzatk.equals(zzko.zzatk)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzko.zzbxg == null || zzko.zzbxg.isEmpty() : this.zzbxg.equals(zzko.zzbxg);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.zzarx == null ? 0 : this.zzarx.hashCode()) + ((getClass().getName().hashCode() + 527) * 31);
        zzkt zzkt = this.zzati;
        hashCode = (zzkt == null ? 0 : zzkt.hashCode()) + (hashCode * 31);
        zzkt = this.zzatj;
        hashCode = ((this.zzatk == null ? 0 : this.zzatk.hashCode()) + (((zzkt == null ? 0 : zzkt.hashCode()) + (hashCode * 31)) * 31)) * 31;
        if (!(this.zzbxg == null || this.zzbxg.isEmpty())) {
            i = this.zzbxg.hashCode();
        }
        return hashCode + i;
    }

    protected final int zza() {
        int zza = super.zza();
        if (this.zzarx != null) {
            zza += zzaby.zzf(1, this.zzarx.intValue());
        }
        if (this.zzati != null) {
            zza += zzaby.zzb(2, this.zzati);
        }
        if (this.zzatj != null) {
            zza += zzaby.zzb(3, this.zzatj);
        }
        if (this.zzatk == null) {
            return zza;
        }
        this.zzatk.booleanValue();
        return zza + (zzaby.zzaq(4) + 1);
    }

    public final void zza(zzaby zzaby) {
        if (this.zzarx != null) {
            zzaby.zze(1, this.zzarx.intValue());
        }
        if (this.zzati != null) {
            zzaby.zza(2, this.zzati);
        }
        if (this.zzatj != null) {
            zzaby.zza(3, this.zzatj);
        }
        if (this.zzatk != null) {
            zzaby.zza(4, this.zzatk.booleanValue());
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
                    this.zzarx = Integer.valueOf(zzabx.zzvh());
                    continue;
                case 18:
                    if (this.zzati == null) {
                        this.zzati = new zzkt();
                    }
                    zzabx.zza(this.zzati);
                    continue;
                case 26:
                    if (this.zzatj == null) {
                        this.zzatj = new zzkt();
                    }
                    zzabx.zza(this.zzatj);
                    continue;
                case 32:
                    this.zzatk = Boolean.valueOf(zzabx.zzvg());
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
