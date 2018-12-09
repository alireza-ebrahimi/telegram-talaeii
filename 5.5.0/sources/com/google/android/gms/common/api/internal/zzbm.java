package com.google.android.gms.common.api.internal;

final class zzbm implements Runnable {
    private final /* synthetic */ zzbl zzkm;

    zzbm(zzbl zzbl) {
        this.zzkm = zzbl;
    }

    public final void run() {
        this.zzkm.zzkk.zzka.disconnect();
    }
}
