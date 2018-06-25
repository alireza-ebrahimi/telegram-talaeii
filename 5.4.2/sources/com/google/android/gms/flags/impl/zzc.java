package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzc implements Callable<Long> {
    private final /* synthetic */ SharedPreferences zzacl;
    private final /* synthetic */ String zzacm;
    private final /* synthetic */ Long zzacr;

    zzc(SharedPreferences sharedPreferences, String str, Long l) {
        this.zzacl = sharedPreferences;
        this.zzacm = str;
        this.zzacr = l;
    }

    public final /* synthetic */ Object call() {
        return Long.valueOf(this.zzacl.getLong(this.zzacm, this.zzacr.longValue()));
    }
}
