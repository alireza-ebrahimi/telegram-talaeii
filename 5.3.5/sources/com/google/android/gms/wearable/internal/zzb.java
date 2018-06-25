package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.internal.zzbq;

final class zzb<T> extends zzn<Status> {
    private T zzgat;
    private zzci<T> zzgbb;
    private zzc<T> zzlrz;

    private zzb(GoogleApiClient googleApiClient, T t, zzci<T> zzci, zzc<T> zzc) {
        super(googleApiClient);
        this.zzgat = zzbq.checkNotNull(t);
        this.zzgbb = (zzci) zzbq.checkNotNull(zzci);
        this.zzlrz = (zzc) zzbq.checkNotNull(zzc);
    }

    static <T> PendingResult<Status> zza(GoogleApiClient googleApiClient, zzc<T> zzc, T t) {
        return googleApiClient.zzd(new zzb(googleApiClient, t, googleApiClient.zzt(t), zzc));
    }

    protected final /* synthetic */ void zza(com.google.android.gms.common.api.Api.zzb zzb) throws RemoteException {
        this.zzlrz.zza((zzhg) zzb, this, this.zzgat, this.zzgbb);
        this.zzgat = null;
        this.zzgbb = null;
    }

    protected final /* synthetic */ Result zzb(Status status) {
        this.zzgat = null;
        this.zzgbb = null;
        return status;
    }
}
