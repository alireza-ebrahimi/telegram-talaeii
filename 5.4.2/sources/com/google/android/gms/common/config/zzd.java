package com.google.android.gms.common.config;

import android.content.Context;

final class zzd extends GservicesValue<Double> {
    zzd(String str, Double d) {
        super(str, d);
    }

    private static Double zza(Context context, String str, Double d) {
        String string = context.getSharedPreferences("gservices-direboot-cache", 0).getString(str, null);
        if (string != null) {
            try {
                d = Double.valueOf(Double.parseDouble(string));
            } catch (NumberFormatException e) {
            }
        }
        return d;
    }

    protected final /* synthetic */ Object retrieve(String str) {
        return GservicesValue.zzmu.zza(this.mKey, (Double) this.mDefaultValue);
    }

    protected final /* synthetic */ Object retrieveFromDirectBootCache(Context context, String str, Object obj) {
        return zza(context, str, (Double) obj);
    }
}
