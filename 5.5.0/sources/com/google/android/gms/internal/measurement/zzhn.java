package com.google.android.gms.internal.measurement;

final class zzhn implements Runnable {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ Object zzaoh;
    private final /* synthetic */ long zzaoi;

    zzhn(zzhl zzhl, String str, String str2, Object obj, long j) {
        this.zzaog = zzhl;
        this.zzanr = str;
        this.val$name = str2;
        this.zzaoh = obj;
        this.zzaoi = j;
    }

    public final void run() {
        this.zzaog.zza(this.zzanr, this.val$name, this.zzaoh, this.zzaoi);
    }
}
