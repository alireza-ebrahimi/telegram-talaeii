package com.google.android.gms.internal.measurement;

final class zzep implements Runnable {
    private final /* synthetic */ zzhj zzafl;
    private final /* synthetic */ zzeo zzafm;

    zzep(zzeo zzeo, zzhj zzhj) {
        this.zzafm = zzeo;
        this.zzafl = zzhj;
    }

    public final void run() {
        this.zzafl.zzgi();
        if (zzec.isMainThread()) {
            this.zzafl.zzge().zzc((Runnable) this);
            return;
        }
        boolean zzef = this.zzafm.zzef();
        this.zzafm.zzye = 0;
        if (zzef) {
            this.zzafm.run();
        }
    }
}
