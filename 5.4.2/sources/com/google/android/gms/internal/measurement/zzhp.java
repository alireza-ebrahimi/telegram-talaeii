package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

final class zzhp implements Callable<String> {
    private final /* synthetic */ zzhl zzaog;

    zzhp(zzhl zzhl) {
        this.zzaog = zzhl;
    }

    public final /* synthetic */ Object call() {
        Object zzjh = this.zzaog.zzgg().zzjh();
        if (zzjh == null) {
            zzhh zzfv = this.zzaog.zzfv();
            if (zzfv.zzge().zzjr()) {
                zzfv.zzgf().zzis().log("Cannot retrieve app instance id from analytics worker thread");
                zzjh = null;
            } else if (zzec.isMainThread()) {
                zzfv.zzgf().zzis().log("Cannot retrieve app instance id from main thread");
                zzjh = null;
            } else {
                long elapsedRealtime = zzfv.zzbt().elapsedRealtime();
                zzjh = zzfv.zzae(120000);
                elapsedRealtime = zzfv.zzbt().elapsedRealtime() - elapsedRealtime;
                if (zzjh == null && elapsedRealtime < 120000) {
                    zzjh = zzfv.zzae(120000 - elapsedRealtime);
                }
            }
            if (zzjh == null) {
                throw new TimeoutException();
            }
            this.zzaog.zzgg().zzbq(zzjh);
        }
        return zzjh;
    }
}
