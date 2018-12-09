package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

final class zzhb implements Callable<byte[]> {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ zzew zzanu;

    zzhb(zzgo zzgo, zzew zzew, String str) {
        this.zzanp = zzgo;
        this.zzanu = zzew;
        this.zzant = str;
    }

    public final /* synthetic */ Object call() {
        this.zzanp.zzajy.zzlg();
        return this.zzanp.zzajy.zza(this.zzanu, this.zzant);
    }
}
