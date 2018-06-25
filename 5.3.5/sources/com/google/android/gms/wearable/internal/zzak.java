package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.ChannelApi.OpenChannelResult;

final class zzak extends zzn<OpenChannelResult> {
    private /* synthetic */ String zzcfe;
    private /* synthetic */ String zzlsy;

    zzak(zzaj zzaj, GoogleApiClient googleApiClient, String str, String str2) {
        this.zzlsy = str;
        this.zzcfe = str2;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        zzhg zzhg = (zzhg) zzb;
        ((zzep) zzhg.zzalw()).zza(new zzha(this), this.zzlsy, this.zzcfe);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzam(status, null);
    }
}
