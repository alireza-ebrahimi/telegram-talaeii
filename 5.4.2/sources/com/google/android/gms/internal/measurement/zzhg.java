package com.google.android.gms.internal.measurement;

final class zzhg implements Runnable {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ String zzanw;
    private final /* synthetic */ String zzanx;
    private final /* synthetic */ long zzany;

    zzhg(zzgo zzgo, String str, String str2, String str3, long j) {
        this.zzanp = zzgo;
        this.zzanw = str;
        this.zzant = str2;
        this.zzanx = str3;
        this.zzany = j;
    }

    public final void run() {
        if (this.zzanw == null) {
            this.zzanp.zzajy.zzlj().zzfz().zza(this.zzant, null);
            return;
        }
        this.zzanp.zzajy.zzlj().zzfz().zza(this.zzant, new zzif(this.zzanx, this.zzanw, this.zzany));
    }
}
