package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zza implements Callable<Boolean> {
    private final /* synthetic */ SharedPreferences zzacl;
    private final /* synthetic */ String zzacm;
    private final /* synthetic */ Boolean zzacn;

    zza(SharedPreferences sharedPreferences, String str, Boolean bool) {
        this.zzacl = sharedPreferences;
        this.zzacm = str;
        this.zzacn = bool;
    }

    public final /* synthetic */ Object call() {
        return Boolean.valueOf(this.zzacl.getBoolean(this.zzacm, this.zzacn.booleanValue()));
    }
}
