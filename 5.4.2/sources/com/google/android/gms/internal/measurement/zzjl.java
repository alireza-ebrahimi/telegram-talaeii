package com.google.android.gms.internal.measurement;

final class zzjl implements Runnable {
    private final /* synthetic */ long zzadj;
    private final /* synthetic */ zzji zzaqg;

    zzjl(zzji zzji, long j) {
        this.zzaqg = zzji;
        this.zzadj = j;
    }

    public final void run() {
        this.zzaqg.zzaf(this.zzadj);
    }
}
