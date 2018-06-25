package com.google.android.gms.internal;

final class zzclx implements Runnable {
    private /* synthetic */ zzclk zzjpy;

    zzclx(zzclk zzclk) {
        this.zzjpy = zzclk;
    }

    public final void run() {
        zzclh zzclh = this.zzjpy;
        zzclh.zzwj();
        zzclh.zzyk();
        zzclh.zzayp().zzbaz().log("Resetting analytics data (FE)");
        zzclh.zzayg().resetAnalyticsData();
    }
}
