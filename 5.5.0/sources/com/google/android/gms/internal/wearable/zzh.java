package com.google.android.gms.internal.wearable;

public final class zzh extends zzn<zzh> {
    private static volatile zzh[] zzfz;
    public String name;
    public zzi zzga;

    public zzh() {
        this.name = TtmlNode.ANONYMOUS_REGION_ID;
        this.zzga = null;
        this.zzhc = null;
        this.zzhl = -1;
    }

    public static zzh[] zzh() {
        if (zzfz == null) {
            synchronized (zzr.zzhk) {
                if (zzfz == null) {
                    zzfz = new zzh[0];
                }
            }
        }
        return zzfz;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzh)) {
            return false;
        }
        zzh zzh = (zzh) obj;
        if (this.name == null) {
            if (zzh.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzh.name)) {
            return false;
        }
        if (this.zzga == null) {
            if (zzh.zzga != null) {
                return false;
            }
        } else if (!this.zzga.equals(zzh.zzga)) {
            return false;
        }
        return (this.zzhc == null || this.zzhc.isEmpty()) ? zzh.zzhc == null || zzh.zzhc.isEmpty() : this.zzhc.equals(zzh.zzhc);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31);
        zzi zzi = this.zzga;
        hashCode = ((zzi == null ? 0 : zzi.hashCode()) + (hashCode * 31)) * 31;
        if (!(this.zzhc == null || this.zzhc.isEmpty())) {
            i = this.zzhc.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzt zza(zzk zzk) {
        while (true) {
            int zzj = zzk.zzj();
            switch (zzj) {
                case 0:
                    break;
                case 10:
                    this.name = zzk.readString();
                    continue;
                case 18:
                    if (this.zzga == null) {
                        this.zzga = new zzi();
                    }
                    zzk.zza(this.zzga);
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
        zzl.zza(1, this.name);
        if (this.zzga != null) {
            zzl.zza(2, this.zzga);
        }
        super.zza(zzl);
    }

    protected final int zzg() {
        int zzg = super.zzg() + zzl.zzb(1, this.name);
        return this.zzga != null ? zzg + zzl.zzb(2, this.zzga) : zzg;
    }
}
