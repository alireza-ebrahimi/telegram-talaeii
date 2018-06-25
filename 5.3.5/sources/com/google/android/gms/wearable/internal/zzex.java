package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.MessageApi.MessageListener;

final class zzex extends zzn<Status> {
    private zzci<MessageListener> zzgbb;
    private MessageListener zzluw;
    private IntentFilter[] zzlux;

    private zzex(GoogleApiClient googleApiClient, MessageListener messageListener, zzci<MessageListener> zzci, IntentFilter[] intentFilterArr) {
        super(googleApiClient);
        this.zzluw = (MessageListener) zzbq.checkNotNull(messageListener);
        this.zzgbb = (zzci) zzbq.checkNotNull(zzci);
        this.zzlux = (IntentFilter[]) zzbq.checkNotNull(intentFilterArr);
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzhg) zzb).zza((zzn) this, this.zzluw, this.zzgbb, this.zzlux);
        this.zzluw = null;
        this.zzgbb = null;
        this.zzlux = null;
    }

    public final /* synthetic */ Result zzb(Status status) {
        this.zzluw = null;
        this.zzgbb = null;
        this.zzlux = null;
        return status;
    }
}
