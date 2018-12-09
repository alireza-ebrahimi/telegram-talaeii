package com.google.android.gms.internal.firebase_auth;

public abstract class zzgn<M extends zzgn<M>> extends zzgt {
    protected zzgp zzxr;

    public /* synthetic */ Object clone() {
        zzgn zzgn = (zzgn) super.zzgn();
        zzgr.zza(this, zzgn);
        return zzgn;
    }

    public void zza(zzgl zzgl) {
        if (this.zzxr != null) {
            for (int i = 0; i < this.zzxr.size(); i++) {
                this.zzxr.zzbc(i).zza(zzgl);
            }
        }
    }

    protected final boolean zza(zzgk zzgk, int i) {
        int position = zzgk.getPosition();
        if (!zzgk.zzn(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzgv zzgv = new zzgv(i, zzgk.zzr(position, zzgk.getPosition() - position));
        zzgq zzgq = null;
        if (this.zzxr == null) {
            this.zzxr = new zzgp();
        } else {
            zzgq = this.zzxr.zzbb(i2);
        }
        if (zzgq == null) {
            zzgq = new zzgq();
            this.zzxr.zza(i2, zzgq);
        }
        zzgq.zza(zzgv);
        return true;
    }

    protected int zzb() {
        int i = 0;
        if (this.zzxr == null) {
            return 0;
        }
        int i2 = 0;
        while (i < this.zzxr.size()) {
            i2 += this.zzxr.zzbc(i).zzb();
            i++;
        }
        return i2;
    }

    public final /* synthetic */ zzgt zzgn() {
        return (zzgn) clone();
    }
}
