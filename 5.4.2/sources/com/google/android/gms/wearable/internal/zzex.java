package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.MessageApi.MessageListener;

final class zzex extends zzn<Status> {
    private ListenerHolder<MessageListener> zzax;
    private IntentFilter[] zzba;
    private MessageListener zzeg;

    private zzex(GoogleApiClient googleApiClient, MessageListener messageListener, ListenerHolder<MessageListener> listenerHolder, IntentFilter[] intentFilterArr) {
        super(googleApiClient);
        this.zzeg = (MessageListener) Preconditions.checkNotNull(messageListener);
        this.zzax = (ListenerHolder) Preconditions.checkNotNull(listenerHolder);
        this.zzba = (IntentFilter[]) Preconditions.checkNotNull(intentFilterArr);
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        this.zzeg = null;
        this.zzax = null;
        this.zzba = null;
        return status;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((zzhg) anyClient).zza((ResultHolder) this, this.zzeg, this.zzax, this.zzba);
        this.zzeg = null;
        this.zzax = null;
        this.zzba = null;
    }
}
