package com.google.android.gms.internal.measurement;

import android.util.Log;

final class zzxb extends zzwu<Double> {
    zzxb(zzxe zzxe, String str, Double d) {
        super(zzxe, str, d);
    }

    private final Double zzfa(String str) {
        try {
            return Double.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            String str2 = this.zzbns;
            Log.e("PhenotypeFlag", new StringBuilder((String.valueOf(str2).length() + 27) + String.valueOf(str).length()).append("Invalid double value for ").append(str2).append(": ").append(str).toString());
            return null;
        }
    }

    protected final /* synthetic */ Object zzex(String str) {
        return zzfa(str);
    }
}
