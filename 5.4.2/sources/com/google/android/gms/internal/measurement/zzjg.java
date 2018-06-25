package com.google.android.gms.internal.measurement;

final class zzjg implements Runnable {
    private final /* synthetic */ Runnable zzabt;
    private final /* synthetic */ zzjs zzaqc;

    zzjg(zzjd zzjd, zzjs zzjs, Runnable runnable) {
        this.zzaqc = zzjs;
        this.zzabt = runnable;
    }

    public final void run() {
        this.zzaqc.zzlg();
        this.zzaqc.zzg(this.zzabt);
        this.zzaqc.zzlb();
    }
}
