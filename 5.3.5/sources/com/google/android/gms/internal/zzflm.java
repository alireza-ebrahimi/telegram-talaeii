package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzflm<M extends zzflm<M>> extends zzfls {
    protected zzflo zzpvl;

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzdck();
    }

    public final <T> T zza(zzfln<M, T> zzfln) {
        if (this.zzpvl == null) {
            return null;
        }
        zzflp zzmz = this.zzpvl.zzmz(zzfln.tag >>> 3);
        return zzmz != null ? zzmz.zzb(zzfln) : null;
    }

    public void zza(zzflk zzflk) throws IOException {
        if (this.zzpvl != null) {
            for (int i = 0; i < this.zzpvl.size(); i++) {
                this.zzpvl.zzna(i).zza(zzflk);
            }
        }
    }

    protected final boolean zza(zzflj zzflj, int i) throws IOException {
        int position = zzflj.getPosition();
        if (!zzflj.zzlg(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzflu zzflu = new zzflu(i, zzflj.zzao(position, zzflj.getPosition() - position));
        zzflp zzflp = null;
        if (this.zzpvl == null) {
            this.zzpvl = new zzflo();
        } else {
            zzflp = this.zzpvl.zzmz(i2);
        }
        if (zzflp == null) {
            zzflp = new zzflp();
            this.zzpvl.zza(i2, zzflp);
        }
        zzflp.zza(zzflu);
        return true;
    }

    public M zzdck() throws CloneNotSupportedException {
        zzflm zzflm = (zzflm) super.zzdcl();
        zzflq.zza(this, zzflm);
        return zzflm;
    }

    public /* synthetic */ zzfls zzdcl() throws CloneNotSupportedException {
        return (zzflm) clone();
    }

    protected int zzq() {
        int i = 0;
        if (this.zzpvl == null) {
            return 0;
        }
        int i2 = 0;
        while (i < this.zzpvl.size()) {
            i2 += this.zzpvl.zzna(i).zzq();
            i++;
        }
        return i2;
    }
}
