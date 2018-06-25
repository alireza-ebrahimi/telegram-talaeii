package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zzi implements Callable<String> {
    private /* synthetic */ SharedPreferences zzhqi;
    private /* synthetic */ String zzhqj;
    private /* synthetic */ String zzhqn;

    zzi(SharedPreferences sharedPreferences, String str, String str2) {
        this.zzhqi = sharedPreferences;
        this.zzhqj = str;
        this.zzhqn = str2;
    }

    public final /* synthetic */ Object call() throws Exception {
        return this.zzhqi.getString(this.zzhqj, this.zzhqn);
    }
}
