package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.DataClient.OnDataChangedListener;

final class zzcw extends UnregisterListenerMethod<zzhg, OnDataChangedListener> {
    private final OnDataChangedListener zzdk;

    private zzcw(OnDataChangedListener onDataChangedListener, ListenerKey<OnDataChangedListener> listenerKey) {
        super(listenerKey);
        this.zzdk = onDataChangedListener;
    }

    protected final /* synthetic */ void unregisterListener(AnyClient anyClient, TaskCompletionSource taskCompletionSource) {
        ((zzhg) anyClient).zza(new zzgg(taskCompletionSource), this.zzdk);
    }
}
