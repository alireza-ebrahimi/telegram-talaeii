package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

final class zze implements Callable<Integer> {
    private /* synthetic */ SharedPreferences zzhqi;
    private /* synthetic */ String zzhqj;
    private /* synthetic */ Integer zzhql;

    zze(SharedPreferences sharedPreferences, String str, Integer num) {
        this.zzhqi = sharedPreferences;
        this.zzhqj = str;
        this.zzhql = num;
    }

    public final /* synthetic */ Object call() throws Exception {
        return Integer.valueOf(this.zzhqi.getInt(this.zzhqj, this.zzhql.intValue()));
    }
}
