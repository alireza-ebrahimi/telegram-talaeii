package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzd implements Callable<String> {
    private final /* synthetic */ SharedPreferences zzacl;
    private final /* synthetic */ String zzacm;
    private final /* synthetic */ String zzact;

    zzd(SharedPreferences sharedPreferences, String str, String str2) {
        this.zzacl = sharedPreferences;
        this.zzacm = str;
        this.zzact = str2;
    }

    public final /* synthetic */ Object call() {
        return this.zzacl.getString(this.zzacm, this.zzact);
    }
}
