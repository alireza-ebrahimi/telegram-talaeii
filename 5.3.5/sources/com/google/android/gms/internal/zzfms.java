package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfms extends zzflm<zzfms> implements Cloneable {
    private static volatile zzfms[] zzpzo;
    private String key;
    private String value;

    public zzfms() {
        this.key = "";
        this.value = "";
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzfms[] zzddf() {
        if (zzpzo == null) {
            synchronized (zzflq.zzpvt) {
                if (zzpzo == null) {
                    zzpzo = new zzfms[0];
                }
            }
        }
        return zzpzo;
    }

    private zzfms zzddg() {
        try {
            return (zzfms) super.zzdck();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzddg();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfms)) {
            return false;
        }
        zzfms zzfms = (zzfms) obj;
        if (this.key == null) {
            if (zzfms.key != null) {
                return false;
            }
        } else if (!this.key.equals(zzfms.key)) {
            return false;
        }
        if (this.value == null) {
            if (zzfms.value != null) {
                return false;
            }
        } else if (!this.value.equals(zzfms.value)) {
            return false;
        }
        return (this.zzpvl == null || this.zzpvl.isEmpty()) ? zzfms.zzpvl == null || zzfms.zzpvl.isEmpty() : this.zzpvl.equals(zzfms.zzpvl);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.value == null ? 0 : this.value.hashCode()) + (((this.key == null ? 0 : this.key.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
        if (!(this.zzpvl == null || this.zzpvl.isEmpty())) {
            i = this.zzpvl.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            switch (zzcxx) {
                case 0:
                    break;
                case 10:
                    this.key = zzflj.readString();
                    continue;
                case 18:
                    this.value = zzflj.readString();
                    continue;
                default:
                    if (!super.zza(zzflj, zzcxx)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (!(this.key == null || this.key.equals(""))) {
            zzflk.zzp(1, this.key);
        }
        if (!(this.value == null || this.value.equals(""))) {
            zzflk.zzp(2, this.value);
        }
        super.zza(zzflk);
    }

    public final /* synthetic */ zzflm zzdck() throws CloneNotSupportedException {
        return (zzfms) clone();
    }

    public final /* synthetic */ zzfls zzdcl() throws CloneNotSupportedException {
        return (zzfms) clone();
    }

    protected final int zzq() {
        int zzq = super.zzq();
        if (!(this.key == null || this.key.equals(""))) {
            zzq += zzflk.zzq(1, this.key);
        }
        return (this.value == null || this.value.equals("")) ? zzq : zzq + zzflk.zzq(2, this.value);
    }
}
