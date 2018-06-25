package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.MessageClient.OnMessageReceivedListener;

final class zzfc extends zzcq<zzhg, OnMessageReceivedListener> {
    private zzci<MessageListener> zzgwf;
    private IntentFilter[] zzlsb;
    private OnMessageReceivedListener zzluz;

    private zzfc(OnMessageReceivedListener onMessageReceivedListener, IntentFilter[] intentFilterArr, zzci<OnMessageReceivedListener> zzci) {
        super(zzci);
        this.zzluz = onMessageReceivedListener;
        this.zzlsb = intentFilterArr;
        this.zzgwf = zzci;
    }

    protected final /* synthetic */ void zzb(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzhg) zzb).zza(new zzgh(taskCompletionSource), this.zzluz, this.zzgwf, this.zzlsb);
    }
}
