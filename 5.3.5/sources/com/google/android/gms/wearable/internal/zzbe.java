package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;

final class zzbe extends zzn<Status> {
    private /* synthetic */ Uri zzkff;
    private /* synthetic */ zzay zzlti;
    private /* synthetic */ long zzltk;
    private /* synthetic */ long zzltl;

    zzbe(zzay zzay, GoogleApiClient googleApiClient, Uri uri, long j, long j2) {
        this.zzlti = zzay;
        this.zzkff = uri;
        this.zzltk = j;
        this.zzltl = j2;
        super(googleApiClient);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzhg) zzb).zza((zzn) this, this.zzlti.zzeia, this.zzkff, this.zzltk, this.zzltl);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
