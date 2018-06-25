package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzdo;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.MessageClient.OnMessageReceivedListener;

final class zzfd extends zzdo<zzhg, OnMessageReceivedListener> {
    private final OnMessageReceivedListener zzluz;

    private zzfd(OnMessageReceivedListener onMessageReceivedListener, zzck<OnMessageReceivedListener> zzck) {
        super(zzck);
        this.zzluz = onMessageReceivedListener;
    }

    protected final /* synthetic */ void zzc(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzhg) zzb).zza(new zzgg(taskCompletionSource), this.zzluz);
    }
}
