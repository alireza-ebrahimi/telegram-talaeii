package com.google.android.gms.common.api.internal;

final class zzbt implements Runnable {
    private /* synthetic */ zzbs zzgac;

    zzbt(zzbs zzbs) {
        this.zzgac = zzbs;
    }

    public final void run() {
        this.zzgac.zzgaa.zzfwd.disconnect();
    }
}
