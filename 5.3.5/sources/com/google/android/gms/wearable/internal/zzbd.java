package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzbd extends zzn<Status> {
    private /* synthetic */ Uri zzkff;
    private /* synthetic */ zzay zzlti;
    private /* synthetic */ boolean zzltj;

    zzbd(zzay zzay, GoogleApiClient googleApiClient, Uri uri, boolean z) {
        this.zzlti = zzay;
        this.zzkff = uri;
        this.zzltj = z;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzhg) zzb).zza((zzn) this, this.zzlti.zzeia, this.zzkff, this.zzltj);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
