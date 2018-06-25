package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzc implements Callable<Boolean> {
    private /* synthetic */ SharedPreferences zzhqi;
    private /* synthetic */ String zzhqj;
    private /* synthetic */ Boolean zzhqk;

    zzc(SharedPreferences sharedPreferences, String str, Boolean bool) {
        this.zzhqi = sharedPreferences;
        this.zzhqj = str;
        this.zzhqk = bool;
    }

    public final /* synthetic */ Object call() throws Exception {
        return Boolean.valueOf(this.zzhqi.getBoolean(this.zzhqj, this.zzhqk.booleanValue()));
    }
}
