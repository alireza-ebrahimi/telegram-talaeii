package com.google.android.gms.internal.wearable;

public final class zzg extends zzn<zzg> {
    public zzh[] zzfy;

    public zzg() {
        this.zzfy = zzh.zzh();
        this.zzhc = null;
        this.zzhl = -1;
    }

    public static zzg zza(byte[] bArr) {
        return (zzg) zzt.zza(new zzg(), bArr, 0, bArr.length);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzg)) {
            return false;
        }
        zzg zzg = (zzg) obj;
        return !zzr.equals(this.zzfy, zzg.zzfy) ? false : (this.zzhc == null || this.zzhc.isEmpty()) ? zzg.zzhc == null || zzg.zzhc.isEmpty() : this.zzhc.equals(zzg.zzhc);
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + 527) * 31) + zzr.hashCode(this.zzfy)) * 31;
        int hashCode2 = (this.zzhc == null || this.zzhc.isEmpty()) ? 0 : this.zzhc.hashCode();
        return hashCode2 + hashCode;
    }

    public final /* synthetic */ zzt zza(zzk zzk) {
        while (true) {
            int zzj = zzk.zzj();
            switch (zzj) {
                case 0:
                    break;
                case 10:
                    int zzb = zzw.zzb(zzk, 10);
                    zzj = this.zzfy == null ? 0 : this.zzfy.length;
                    Object obj = new zzh[(zzb + zzj)];
                    if (zzj != 0) {
                        System.arraycopy(this.zzfy, 0, obj, 0, zzj);
                    }
                    while (zzj < obj.length - 1) {
                        obj[zzj] = new zzh();
                        zzk.zza(obj[zzj]);
                        zzk.zzj();
                        zzj++;
                    }
                    obj[zzj] = new zzh();
                    zzk.zza(obj[zzj]);
                    this.zzfy = obj;
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

    public final void zza(zzl zzl) {
        if (this.zzfy != null && this.zzfy.length > 0) {
            for (zzt zzt : this.zzfy) {
                if (zzt != null) {
                    zzl.zza(1, zzt);
                }
            }
        }
        super.zza(zzl);
    }

    protected final int zzg() {
        int zzg = super.zzg();
        if (this.zzfy != null && this.zzfy.length > 0) {
            for (zzt zzt : this.zzfy) {
                if (zzt != null) {
                    zzg += zzl.zzb(1, zzt);
                }
            }
        }
        return zzg;
    }
}
