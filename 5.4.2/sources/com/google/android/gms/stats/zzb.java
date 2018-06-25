package com.google.android.gms.stats;

final class zzb implements Runnable {
    private final /* synthetic */ WakeLock zzaei;

    zzb(WakeLock wakeLock) {
        this.zzaei = wakeLock;
    }

    public final void run() {
        this.zzaei.zzn(0);
    }
}
