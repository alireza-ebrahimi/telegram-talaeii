package com.google.android.gms.common.api.internal;

final class zzch implements Runnable {
    private /* synthetic */ String zzas;
    private /* synthetic */ LifecycleCallback zzgaq;
    private /* synthetic */ zzcg zzgar;

    zzch(zzcg zzcg, LifecycleCallback lifecycleCallback, String str) {
        this.zzgar = zzcg;
        this.zzgaq = lifecycleCallback;
        this.zzas = str;
    }

    public final void run() {
        if (this.zzgar.zzcfl > 0) {
            this.zzgaq.onCreate(this.zzgar.zzgap != null ? this.zzgar.zzgap.getBundle(this.zzas) : null);
        }
        if (this.zzgar.zzcfl >= 2) {
            this.zzgaq.onStart();
        }
        if (this.zzgar.zzcfl >= 3) {
            this.zzgaq.onResume();
        }
        if (this.zzgar.zzcfl >= 4) {
            this.zzgaq.onStop();
        }
        if (this.zzgar.zzcfl >= 5) {
            this.zzgaq.onDestroy();
        }
    }
}
