package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import javax.annotation.Nullable;

final class zzan extends zzn<Status> {
    private final String zzce;
    private ChannelListener zzcf;

    zzan(GoogleApiClient googleApiClient, ChannelListener channelListener, @Nullable String str) {
        super(googleApiClient);
        this.zzcf = (ChannelListener) Preconditions.checkNotNull(channelListener);
        this.zzce = str;
    }

    public final /* synthetic */ Result createFailedResult(Status status) {
        this.zzcf = null;
        return status;
    }

    protected final /* synthetic */ void doExecute(AnyClient anyClient) {
        ((zzhg) anyClient).zza(this, this.zzcf, this.zzce);
        this.zzcf = null;
    }
}
