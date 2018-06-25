package com.google.android.gms.common.api.internal;

final class zzdl implements zzdn {
    private /* synthetic */ zzdk zzgbu;

    zzdl(zzdk zzdk) {
        this.zzgbu = zzdk;
    }

    public final void zzc(BasePendingResult<?> basePendingResult) {
        this.zzgbu.zzgbs.remove(basePendingResult);
    }
}
