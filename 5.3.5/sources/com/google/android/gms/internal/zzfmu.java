package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmu extends zzflm<zzfmu> {
    private static volatile zzfmu[] zzpzr;
    public String zzpzs;

    public zzfmu() {
        this.zzpzs = "";
        this.zzpvl = null;
        this.zzpnr = -1;
    }

    public static zzfmu[] zzddi() {
        if (zzpzr == null) {
            synchronized (zzflq.zzpvt) {
                if (zzpzr == null) {
                    zzpzr = new zzfmu[0];
                }
            }
        }
        return zzpzr;
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zzcxx = zzflj.zzcxx();
            switch (zzcxx) {
                case 0:
                    break;
                case 10:
                    this.zzpzs = zzflj.readString();
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
        if (!(this.zzpzs == null || this.zzpzs.equals(""))) {
            zzflk.zzp(1, this.zzpzs);
        }
        super.zza(zzflk);
    }

    protected final int zzq() {
        int zzq = super.zzq();
        return (this.zzpzs == null || this.zzpzs.equals("")) ? zzq : zzq + zzflk.zzq(1, this.zzpzs);
    }
}
