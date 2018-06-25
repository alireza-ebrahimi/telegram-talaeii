package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;

final class zzcb extends zzn<DeleteDataItemsResult> {
    private /* synthetic */ Uri zzkff;
    private /* synthetic */ int zzltv;

    zzcb(zzbw zzbw, GoogleApiClient googleApiClient, Uri uri, int i) {
        this.zzkff = uri;
        this.zzltv = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zzb(new zzgp(this), this.zzkff, this.zzltv);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new zzch(status, 0);
    }
}
