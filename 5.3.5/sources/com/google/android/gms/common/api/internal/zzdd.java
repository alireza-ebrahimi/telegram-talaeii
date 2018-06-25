package com.google.android.gms.common.api.internal;

final class zzdd implements Runnable {
    private /* synthetic */ String zzas;
    private /* synthetic */ LifecycleCallback zzgaq;
    private /* synthetic */ zzdc zzgbg;

    zzdd(zzdc zzdc, LifecycleCallback lifecycleCallback, String str) {
        this.zzgbg = zzdc;
        this.zzgaq = lifecycleCallback;
        this.zzas = str;
    }

    public final void run() {
        if (this.zzgbg.zzcfl > 0) {
            this.zzgaq.onCreate(this.zzgbg.zzgap != null ? this.zzgbg.zzgap.getBundle(this.zzas) : null);
        }
        if (this.zzgbg.zzcfl >= 2) {
            this.zzgaq.onStart();
        }
        if (this.zzgbg.zzcfl >= 3) {
            this.zzgaq.onResume();
        }
        if (this.zzgbg.zzcfl >= 4) {
            this.zzgaq.onStop();
        }
        if (this.zzgbg.zzcfl >= 5) {
            this.zzgaq.onDestroy();
        }
    }
}
