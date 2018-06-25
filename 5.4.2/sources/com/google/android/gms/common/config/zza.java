package com.google.android.gms.common.config;

import android.content.Context;

final class zza extends GservicesValue<Boolean> {
    zza(String str, Boolean bool) {
        super(str, bool);
    }

    private static Boolean zza(Context context, String str, Boolean bool) {
        String string = context.getSharedPreferences("gservices-direboot-cache", 0).getString(str, null);
        if (string != null) {
            try {
                bool = Boolean.valueOf(Boolean.parseBoolean(string));
            } catch (NumberFormatException e) {
            }
        }
        return bool;
    }

    protected final /* synthetic */ Object retrieve(String str) {
        return GservicesValue.zzmu.zza(this.mKey, (Boolean) this.mDefaultValue);
    }

    protected final /* synthetic */ Object retrieveFromDirectBootCache(Context context, String str, Object obj) {
        return zza(context, str, (Boolean) obj);
    }
}
