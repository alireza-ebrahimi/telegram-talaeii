package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;

public abstract class zzm<R extends Result, A extends zzb> extends BasePendingResult<R> implements zzn<R> {
    private final Api<?> zzfop;
    private final zzc<A> zzfus;

    @Deprecated
    protected zzm(@NonNull zzc<A> zzc, @NonNull GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzbq.checkNotNull(googleApiClient, "GoogleApiClient must not be null"));
        this.zzfus = (zzc) zzbq.checkNotNull(zzc);
        this.zzfop = null;
    }

    protected zzm(@NonNull Api<?> api, @NonNull GoogleApiClient googleApiClient) {
        super((GoogleApiClient) zzbq.checkNotNull(googleApiClient, "GoogleApiClient must not be null"));
        zzbq.checkNotNull(api, "Api must not be null");
        this.zzfus = api.zzahm();
        this.zzfop = api;
    }

    private final void zzc(@NonNull RemoteException remoteException) {
        zzu(new Status(8, remoteException.getLocalizedMessage(), null));
    }

    @Hide
    public /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }

    @Hide
    protected abstract void zza(@NonNull A a) throws RemoteException;

    @Hide
    public final zzc<A> zzahm() {
        return this.zzfus;
    }

    @Hide
    public final Api<?> zzaht() {
        return this.zzfop;
    }

    @Hide
    public final void zzb(@NonNull A a) throws DeadObjectException {
        if (a instanceof zzbz) {
            zzb zzanb = zzbz.zzanb();
        }
        try {
            zza(zzanb);
        } catch (RemoteException e) {
            zzc(e);
            throw e;
        } catch (RemoteException e2) {
            zzc(e2);
        }
    }

    @Hide
    public final void zzu(@NonNull Status status) {
        zzbq.checkArgument(!status.isSuccess(), "Failed result must not be success");
        setResult(zzb(status));
    }
}
