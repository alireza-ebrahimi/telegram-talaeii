package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

final class zzhu implements Runnable {
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ AtomicReference zzaof;
    private final /* synthetic */ zzhl zzaog;

    zzhu(zzhl zzhl, AtomicReference atomicReference, String str, String str2, String str3) {
        this.zzaog = zzhl;
        this.zzaof = atomicReference;
        this.zzant = str;
        this.zzanr = str2;
        this.zzans = str3;
    }

    public final void run() {
        this.zzaog.zzacw.zzfy().zza(this.zzaof, this.zzant, this.zzanr, this.zzans);
    }
}
