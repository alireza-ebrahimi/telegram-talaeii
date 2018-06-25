package com.google.android.gms.internal;

final class zze implements Runnable {
    private /* synthetic */ zzr zzn;
    private /* synthetic */ zzd zzo;

    zze(zzd zzd, zzr zzr) {
        this.zzo = zzd;
        this.zzn = zzr;
    }

    public final void run() {
        try {
            this.zzo.zzi.put(this.zzn);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
