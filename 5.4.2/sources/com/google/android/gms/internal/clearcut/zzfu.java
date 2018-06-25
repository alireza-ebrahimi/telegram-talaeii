package com.google.android.gms.internal.clearcut;

public class zzfu<M extends zzfu<M>> extends zzfz {
    protected zzfw zzrj;

    public /* synthetic */ Object clone() {
        return zzeo();
    }

    public void zza(zzfs zzfs) {
        if (this.zzrj != null) {
            for (int i = 0; i < this.zzrj.size(); i++) {
                this.zzrj.zzaq(i).zza(zzfs);
            }
        }
    }

    protected int zzen() {
        if (this.zzrj != null) {
            for (int i = 0; i < this.zzrj.size(); i++) {
                this.zzrj.zzaq(i).zzen();
            }
        }
        return 0;
    }

    public M zzeo() {
        zzfu zzfu = (zzfu) super.zzep();
        zzfy.zza(this, zzfu);
        return zzfu;
    }

    public /* synthetic */ zzfz zzep() {
        return (zzfu) clone();
    }
}
