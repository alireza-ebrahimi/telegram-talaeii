package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.ChannelClient.ChannelCallback;

final class zzat extends zzcq<zzhg, ChannelCallback> {
    private String zzlnm;
    private IntentFilter[] zzlsb;
    private ChannelListener zzltd;
    private zzci<ChannelListener> zzlte;

    zzat(ChannelListener channelListener, String str, IntentFilter[] intentFilterArr, zzci<ChannelCallback> zzci, zzci<ChannelListener> zzci2) {
        super(zzci);
        this.zzltd = channelListener;
        this.zzlsb = intentFilterArr;
        this.zzlnm = str;
        this.zzlte = zzci2;
    }

    protected final /* synthetic */ void zzb(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzhg) zzb).zza(new zzgh(taskCompletionSource), this.zzltd, this.zzlte, this.zzlnm, this.zzlsb);
    }
}
