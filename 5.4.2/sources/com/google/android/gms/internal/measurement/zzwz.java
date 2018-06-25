package com.google.android.gms.internal.measurement;

import android.util.Log;

final class zzwz extends zzwu<Integer> {
    zzwz(zzxe zzxe, String str, Integer num) {
        super(zzxe, str, num);
    }

    private final Integer zzez(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            String str2 = this.zzbns;
            Log.e("PhenotypeFlag", new StringBuilder((String.valueOf(str2).length() + 28) + String.valueOf(str).length()).append("Invalid integer value for ").append(str2).append(": ").append(str).toString());
            return null;
        }
    }

    protected final /* synthetic */ Object zzex(String str) {
        return zzez(str);
    }
}
