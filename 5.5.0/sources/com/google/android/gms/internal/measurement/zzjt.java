package com.google.android.gms.internal.measurement;

final class zzjt implements Runnable {
    private final /* synthetic */ zzjx zzare;
    private final /* synthetic */ zzjs zzarf;

    zzjt(zzjs zzjs, zzjx zzjx) {
        this.zzarf = zzjs;
        this.zzare = zzjx;
    }

    public final void run() {
        this.zzarf.zza(this.zzare);
        this.zzarf.start();
    }
}
