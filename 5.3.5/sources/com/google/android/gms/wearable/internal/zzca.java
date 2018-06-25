package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataItemBuffer;

final class zzca extends zzn<DataItemBuffer> {
    private /* synthetic */ Uri zzkff;
    private /* synthetic */ int zzltv;

    zzca(zzbw zzbw, GoogleApiClient googleApiClient, Uri uri, int i) {
        this.zzkff = uri;
        this.zzltv = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zza(new zzgw(this), this.zzkff, this.zzltv);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new DataItemBuffer(DataHolder.zzbz(status.getStatusCode()));
    }
}
