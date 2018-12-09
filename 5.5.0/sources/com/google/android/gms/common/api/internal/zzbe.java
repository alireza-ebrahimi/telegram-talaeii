package com.google.android.gms.common.api.internal;

abstract class zzbe {
    private final zzbc zzjg;

    protected zzbe(zzbc zzbc) {
        this.zzjg = zzbc;
    }

    protected abstract void zzaq();

    public final void zzc(zzbd zzbd) {
        zzbd.zzga.lock();
        try {
            if (zzbd.zzjc == this.zzjg) {
                zzaq();
                zzbd.zzga.unlock();
            }
        } finally {
            zzbd.zzga.unlock();
        }
    }
}
