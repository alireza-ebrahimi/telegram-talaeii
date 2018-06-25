package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

final class zzgx implements Callable<List<zzee>> {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;
    private final /* synthetic */ String zzant;

    zzgx(zzgo zzgo, String str, String str2, String str3) {
        this.zzanp = zzgo;
        this.zzant = str;
        this.zzanr = str2;
        this.zzans = str3;
    }

    public final /* synthetic */ Object call() {
        this.zzanp.zzajy.zzlg();
        return this.zzanp.zzajy.zzje().zzc(this.zzant, this.zzanr, this.zzans);
    }
}
