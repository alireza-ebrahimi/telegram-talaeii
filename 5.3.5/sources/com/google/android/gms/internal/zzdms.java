package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.Wallet.zzb;

final class zzdms extends zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ String zzlpq;
    private /* synthetic */ String zzlpr;

    zzdms(zzdmo zzdmo, GoogleApiClient googleApiClient, String str, String str2, int i) {
        this.zzlpq = str;
        this.zzlpr = str2;
        this.val$requestCode = i;
        super(googleApiClient);
    }

    protected final void zza(zzdmv zzdmv) {
        zzdmv.zzc(this.zzlpq, this.zzlpr, this.val$requestCode);
        setResult(Status.zzftq);
    }
}
