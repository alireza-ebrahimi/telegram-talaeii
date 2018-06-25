package com.google.android.gms.internal;

import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

public final class zzn extends Thread {
    private final zzm zzaa;
    private final zzb zzj;
    private final zzaa zzk;
    private volatile boolean zzl = false;
    private final BlockingQueue<zzr<?>> zzz;

    public zzn(BlockingQueue<zzr<?>> blockingQueue, zzm zzm, zzb zzb, zzaa zzaa) {
        this.zzz = blockingQueue;
        this.zzaa = zzm;
        this.zzj = zzb;
        this.zzk = zzaa;
    }

    private final void processRequest() throws InterruptedException {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        zzr zzr = (zzr) this.zzz.take();
        try {
            zzr.zzb("network-queue-take");
            zzr.isCanceled();
            TrafficStats.setThreadStatsTag(zzr.zzd());
            zzp zzc = this.zzaa.zzc(zzr);
            zzr.zzb("network-http-complete");
            if (zzc.zzac && zzr.zzk()) {
                zzr.zzc("not-modified");
                zzr.zzl();
                return;
            }
            zzx zza = zzr.zza(zzc);
            zzr.zzb("network-parse-complete");
            if (zzr.zzg() && zza.zzbg != null) {
                this.zzj.zza(zzr.getUrl(), zza.zzbg);
                zzr.zzb("network-cache-written");
            }
            zzr.zzj();
            this.zzk.zzb(zzr, zza);
            zzr.zza(zza);
        } catch (zzae e) {
            e.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.zzk.zza(zzr, e);
            zzr.zzl();
        } catch (Throwable e2) {
            zzaf.zza(e2, "Unhandled exception %s", e2.toString());
            zzae zzae = new zzae(e2);
            zzae.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.zzk.zza(zzr, zzae);
            zzr.zzl();
        }
    }

    public final void quit() {
        this.zzl = true;
        interrupt();
    }

    public final void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                processRequest();
            } catch (InterruptedException e) {
                if (this.zzl) {
                    return;
                }
            }
        }
    }
}
