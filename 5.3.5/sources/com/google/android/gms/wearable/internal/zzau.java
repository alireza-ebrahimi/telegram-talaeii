package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzdo;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.ChannelClient.ChannelCallback;

final class zzau extends zzdo<zzhg, ChannelCallback> {
    private final String zzlnm;
    private final ChannelListener zzltd;

    zzau(ChannelListener channelListener, String str, zzck<ChannelCallback> zzck) {
        super(zzck);
        this.zzltd = channelListener;
        this.zzlnm = str;
    }

    protected final /* synthetic */ void zzc(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzhg) zzb).zza(new zzgg(taskCompletionSource), this.zzltd, this.zzlnm);
    }
}
