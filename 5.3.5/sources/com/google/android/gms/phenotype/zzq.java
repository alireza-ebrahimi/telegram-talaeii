package com.google.android.gms.phenotype;

import com.google.android.gms.internal.zzdnm;

final /* synthetic */ class zzq implements zza {
    private final String zzdiu;
    private final boolean zzkha = false;

    zzq(String str, boolean z) {
        this.zzdiu = str;
    }

    public final Object zzbel() {
        return Boolean.valueOf(zzdnm.zza(PhenotypeFlag.zzaiq.getContentResolver(), this.zzdiu, this.zzkha));
    }
}
