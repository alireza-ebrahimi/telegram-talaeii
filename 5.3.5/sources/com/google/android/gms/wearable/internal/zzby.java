package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi.DataItemResult;

final class zzby extends zzn<DataItemResult> {
    private /* synthetic */ Uri zzkff;

    zzby(zzbw zzbw, GoogleApiClient googleApiClient, Uri uri) {
        this.zzkff = uri;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zza(new zzgv(this), this.zzkff);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new zzcg(status, null);
    }
}
