package com.google.android.gms.internal.measurement;

final class zzha implements Runnable {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ zzew zzanu;

    zzha(zzgo zzgo, zzew zzew, String str) {
        this.zzanp = zzgo;
        this.zzanu = zzew;
        this.zzant = str;
    }

    public final void run() {
        this.zzanp.zzajy.zzlg();
        this.zzanp.zzajy.zzc(this.zzanu, this.zzant);
    }
}
