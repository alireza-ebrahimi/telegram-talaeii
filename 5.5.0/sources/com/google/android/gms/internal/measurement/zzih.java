package com.google.android.gms.internal.measurement;

import android.os.Bundle;

final class zzih implements Runnable {
    private final /* synthetic */ boolean zzapb;
    private final /* synthetic */ zzif zzapc;
    private final /* synthetic */ zzif zzapd;
    private final /* synthetic */ zzig zzape;

    zzih(zzig zzig, boolean z, zzif zzif, zzif zzif2) {
        this.zzape = zzig;
        this.zzapb = z;
        this.zzapc = zzif;
        this.zzapd = zzif2;
    }

    public final void run() {
        if (this.zzapb && this.zzape.zzaov != null) {
            this.zzape.zza(this.zzape.zzaov);
        }
        boolean z = (this.zzapc != null && this.zzapc.zzaot == this.zzapd.zzaot && zzkc.zzs(this.zzapc.zzaos, this.zzapd.zzaos) && zzkc.zzs(this.zzapc.zzul, this.zzapd.zzul)) ? false : true;
        if (z) {
            Bundle bundle = new Bundle();
            zzig.zza(this.zzapd, bundle, true);
            if (this.zzapc != null) {
                if (this.zzapc.zzul != null) {
                    bundle.putString("_pn", this.zzapc.zzul);
                }
                bundle.putString("_pc", this.zzapc.zzaos);
                bundle.putLong("_pi", this.zzapc.zzaot);
            }
            this.zzape.zzfv().zza("auto", "_vs", bundle);
        }
        this.zzape.zzaov = this.zzapd;
        this.zzape.zzfy().zzb(this.zzapd);
    }
}
