package com.google.android.gms.internal.measurement;

final class zzhr implements Runnable {
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ long zzaok;

    zzhr(zzhl zzhl, long j) {
        this.zzaog = zzhl;
        this.zzaok = j;
    }

    public final void run() {
        boolean z = true;
        zzhh zzhh = this.zzaog;
        long j = this.zzaok;
        zzhh.zzab();
        zzhh.zzfs();
        zzhh.zzch();
        zzhh.zzgf().zziy().log("Resetting analytics data (FE)");
        zzhh.zzgd().zzks();
        if (zzhh.zzgh().zzaz(zzhh.zzfw().zzah())) {
            zzhh.zzgg().zzaki.set(j);
        }
        boolean isEnabled = zzhh.zzacw.isEnabled();
        if (!zzhh.zzgh().zzhj()) {
            zzhh.zzgg().zzh(!isEnabled);
        }
        zzhh.zzfy().resetAnalyticsData();
        if (isEnabled) {
            z = false;
        }
        zzhh.zzaoe = z;
    }
}
