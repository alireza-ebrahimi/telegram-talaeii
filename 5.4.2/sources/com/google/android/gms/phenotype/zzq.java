package com.google.android.gms.phenotype;

import com.google.android.gms.internal.phenotype.zzf;

final /* synthetic */ class zzq implements zza {
    private final String zzav;
    private final boolean zzaw = false;

    zzq(String str, boolean z) {
        this.zzav = str;
    }

    public final Object zzh() {
        return Boolean.valueOf(zzf.zza(PhenotypeFlag.zzal.getContentResolver(), this.zzav, this.zzaw));
    }
}
