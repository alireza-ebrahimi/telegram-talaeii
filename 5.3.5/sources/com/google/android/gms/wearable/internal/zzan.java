package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;

final class zzan extends zzn<Status> {
    private final String zzeia;
    private ChannelListener zzlta;

    zzan(GoogleApiClient googleApiClient, ChannelListener channelListener, String str) {
        super(googleApiClient);
        this.zzlta = (ChannelListener) zzbq.checkNotNull(channelListener);
        this.zzeia = str;
    }

    protected final /* synthetic */ void zza(zzb zzb) throws RemoteException {
        ((zzhg) zzb).zza(this, this.zzlta, this.zzeia);
        this.zzlta = null;
    }

    public final /* synthetic */ Result zzb(Status status) {
        this.zzlta = null;
        return status;
    }
}
