package com.google.android.gms.internal.measurement;

import android.util.Log;

final class zzxa extends zzwu<Boolean> {
    zzxa(zzxe zzxe, String str, Boolean bool) {
        super(zzxe, str, bool);
    }

    protected final /* synthetic */ Object zzex(String str) {
        if (zzwp.zzbmu.matcher(str).matches()) {
            return Boolean.valueOf(true);
        }
        if (zzwp.zzbmv.matcher(str).matches()) {
            return Boolean.valueOf(false);
        }
        String str2 = this.zzbns;
        Log.e("PhenotypeFlag", new StringBuilder((String.valueOf(str2).length() + 28) + String.valueOf(str).length()).append("Invalid boolean value for ").append(str2).append(": ").append(str).toString());
        return null;
    }
}
