package com.google.android.gms.internal.wearable;

public final class zzi extends zzn<zzi> {
    private static volatile zzi[] zzgb;
    public int type;
    public zzj zzgc;

    public zzi() {
        this.type = 1;
        this.zzgc = null;
        this.zzhc = null;
        this.zzhl = -1;
    }

    private final zzi zzb(zzk zzk) {
        while (true) {
            int zzj = zzk.zzj();
            switch (zzj) {
                case 0:
                    break;
                case 8:
                    int position = zzk.getPosition();
                    try {
                        int zzk2 = zzk.zzk();
                        if (zzk2 <= 0 || zzk2 > 15) {
                            throw new IllegalArgumentException(zzk2 + " is not a valid enum Type");
                        }
                        this.type = zzk2;
                        continue;
                    } catch (IllegalArgumentException e) {
                        zzk.zzg(position);
                        zza(zzk, zzj);
                        break;
                    }
                case 18:
                    if (this.zzgc == null) {
                        this.zzgc = new zzj();
                    }
                    zzk.zza(this.zzgc);
                    continue;
                default:
                    if (!super.zza(zzk, zzj)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    public static zzi[] zzi() {
        if (zzgb == null) {
            synchronized (zzr.zzhk) {
                if (zzgb == null) {
                    zzgb = new zzi[0];
                }
            }
        }
        return zzgb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzi)) {
            return false;
        }
        zzi zzi = (zzi) obj;
        if (this.type != zzi.type) {
            return false;
        }
        if (this.zzgc == null) {
            if (zzi.zzgc != null) {
                return false;
            }
        } else if (!this.zzgc.equals(zzi.zzgc)) {
            return false;
        }
        return (this.zzhc == null || this.zzhc.isEmpty()) ? zzi.zzhc == null || zzi.zzhc.isEmpty() : this.zzhc.equals(zzi.zzhc);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((getClass().getName().hashCode() + 527) * 31) + this.type;
        zzj zzj = this.zzgc;
        hashCode = ((zzj == null ? 0 : zzj.hashCode()) + (hashCode * 31)) * 31;
        if (!(this.zzhc == null || this.zzhc.isEmpty())) {
            i = this.zzhc.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzt zza(zzk zzk) {
        return zzb(zzk);
    }

    public final void zza(zzl zzl) {
        zzl.zzd(1, this.type);
        if (this.zzgc != null) {
            zzl.zza(2, this.zzgc);
        }
        super.zza(zzl);
    }

    protected final int zzg() {
        int zzg = super.zzg() + zzl.zze(1, this.type);
        return this.zzgc != null ? zzg + zzl.zzb(2, this.zzgc) : zzg;
    }
}
