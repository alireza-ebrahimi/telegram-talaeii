package com.google.android.gms.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

final class zzclv implements Callable<String> {
    private /* synthetic */ zzclk zzjpy;

    zzclv(zzclk zzclk) {
        this.zzjpy = zzclk;
    }

    public final /* synthetic */ Object call() throws Exception {
        Object zzbbf = this.zzjpy.zzayq().zzbbf();
        if (zzbbf == null) {
            zzclh zzayd = this.zzjpy.zzayd();
            if (zzayd.zzayo().zzbbk()) {
                zzayd.zzayp().zzbau().log("Cannot retrieve app instance id from analytics worker thread");
                zzbbf = null;
            } else {
                zzayd.zzayo();
                if (zzcke.zzas()) {
                    zzayd.zzayp().zzbau().log("Cannot retrieve app instance id from main thread");
                    zzbbf = null;
                } else {
                    long elapsedRealtime = zzayd.zzxx().elapsedRealtime();
                    zzbbf = zzayd.zzbd(120000);
                    elapsedRealtime = zzayd.zzxx().elapsedRealtime() - elapsedRealtime;
                    if (zzbbf == null && elapsedRealtime < 120000) {
                        zzbbf = zzayd.zzbd(120000 - elapsedRealtime);
                    }
                }
            }
            if (zzbbf == null) {
                throw new TimeoutException();
            }
            this.zzjpy.zzayq().zzjx(zzbbf);
        }
        return zzbbf;
    }
}
