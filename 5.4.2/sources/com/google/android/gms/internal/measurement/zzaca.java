package com.google.android.gms.internal.measurement;

public abstract class zzaca<M extends zzaca<M>> extends zzacg {
    protected zzacc zzbxg;

    public /* synthetic */ Object clone() {
        zzaca zzaca = (zzaca) super.zzvo();
        zzace.zza(this, zzaca);
        return zzaca;
    }

    protected int zza() {
        int i = 0;
        if (this.zzbxg == null) {
            return 0;
        }
        int i2 = 0;
        while (i < this.zzbxg.size()) {
            i2 += this.zzbxg.zzau(i).zza();
            i++;
        }
        return i2;
    }

    public final <T> T zza(zzacb<M, T> zzacb) {
        if (this.zzbxg == null) {
            return null;
        }
        zzacd zzat = this.zzbxg.zzat(zzacb.tag >>> 3);
        return zzat != null ? zzat.zzb(zzacb) : null;
    }

    public void zza(zzaby zzaby) {
        if (this.zzbxg != null) {
            for (int i = 0; i < this.zzbxg.size(); i++) {
                this.zzbxg.zzau(i).zza(zzaby);
            }
        }
    }

    protected final boolean zza(zzabx zzabx, int i) {
        int position = zzabx.getPosition();
        if (!zzabx.zzak(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzaci zzaci = new zzaci(i, zzabx.zzc(position, zzabx.getPosition() - position));
        zzacd zzacd = null;
        if (this.zzbxg == null) {
            this.zzbxg = new zzacc();
        } else {
            zzacd = this.zzbxg.zzat(i2);
        }
        if (zzacd == null) {
            zzacd = new zzacd();
            this.zzbxg.zza(i2, zzacd);
        }
        zzacd.zza(zzaci);
        return true;
    }

    public final /* synthetic */ zzacg zzvo() {
        return (zzaca) clone();
    }
}
