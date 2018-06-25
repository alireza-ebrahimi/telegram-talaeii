package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;

final class zzbb extends zzn<GetInputStreamResult> {
    private /* synthetic */ zzay zzlti;

    zzbb(zzay zzay, GoogleApiClient googleApiClient) {
        this.zzlti = zzay;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        String zza = this.zzlti.zzeia;
        zzei zzbr = new zzbr();
        ((zzep) zzhg.zzalw()).zza(new zzgs(this, zzbr), zzbr, zza);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzbg(status, null);
    }
}
