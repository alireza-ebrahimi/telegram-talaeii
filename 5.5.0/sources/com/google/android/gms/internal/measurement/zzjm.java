package com.google.android.gms.internal.measurement;

final class zzjm implements Runnable {
    private final /* synthetic */ long zzadj;
    private final /* synthetic */ zzji zzaqg;

    zzjm(zzji zzji, long j) {
        this.zzaqg = zzji;
        this.zzadj = j;
    }

    public final void run() {
        this.zzaqg.zzag(this.zzadj);
    }
}
