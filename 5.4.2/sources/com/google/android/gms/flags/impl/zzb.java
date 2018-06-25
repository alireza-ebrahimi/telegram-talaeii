package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzb implements Callable<Integer> {
    private final /* synthetic */ SharedPreferences zzacl;
    private final /* synthetic */ String zzacm;
    private final /* synthetic */ Integer zzacp;

    zzb(SharedPreferences sharedPreferences, String str, Integer num) {
        this.zzacl = sharedPreferences;
        this.zzacm = str;
        this.zzacp = num;
    }

    public final /* synthetic */ Object call() {
        return Integer.valueOf(this.zzacl.getInt(this.zzacm, this.zzacp.intValue()));
    }
}
