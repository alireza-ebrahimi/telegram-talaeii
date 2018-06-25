package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi.GetCapabilityResult;

final class zzp extends zzn<GetCapabilityResult> {
    private /* synthetic */ String zzlsm;
    private /* synthetic */ int zzlsn;

    zzp(zzo zzo, GoogleApiClient googleApiClient, String str, int i) {
        this.zzlsm = str;
        this.zzlsn = i;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zza(new zzgr(this), this.zzlsm, this.zzlsn);
    }

    protected final /* synthetic */ Result zzb(Status status) {
        return new zzy(status, null);
    }
}
