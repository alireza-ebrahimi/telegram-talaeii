package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

final class zzgv implements Callable<List<zzkb>> {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;
    private final /* synthetic */ String zzant;

    zzgv(zzgo zzgo, String str, String str2, String str3) {
        this.zzanp = zzgo;
        this.zzant = str;
        this.zzanr = str2;
        this.zzans = str3;
    }

    public final /* synthetic */ Object call() {
        this.zzanp.zzajy.zzlg();
        return this.zzanp.zzajy.zzje().zzb(this.zzant, this.zzanr, this.zzans);
    }
}
