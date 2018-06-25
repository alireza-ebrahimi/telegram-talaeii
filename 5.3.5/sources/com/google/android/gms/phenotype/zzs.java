package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.phenotype.PhenotypeFlag.Factory;

final class zzs extends PhenotypeFlag<String> {
    zzs(Factory factory, String str, String str2) {
        super(factory, str, str2);
    }

    private final String zzc(SharedPreferences sharedPreferences) {
        try {
            return sharedPreferences.getString(this.zzkgv, null);
        } catch (Throwable e) {
            Throwable th = e;
            String str = "PhenotypeFlag";
            String str2 = "Invalid string value in SharedPreferences for ";
            String valueOf = String.valueOf(this.zzkgv);
            Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), th);
            return null;
        }
    }

    public final /* synthetic */ Object zzb(SharedPreferences sharedPreferences) {
        return zzc(sharedPreferences);
    }

    public final /* synthetic */ Object zzkz(String str) {
        return str;
    }
}
