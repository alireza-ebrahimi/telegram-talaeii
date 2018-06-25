package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzdo;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener;

final class zzag extends zzdo<zzhg, OnCapabilityChangedListener> {
    private final OnCapabilityChangedListener zzlsw;

    private zzag(OnCapabilityChangedListener onCapabilityChangedListener, zzck<OnCapabilityChangedListener> zzck) {
        super(zzck);
        this.zzlsw = onCapabilityChangedListener;
    }

    protected final /* synthetic */ void zzc(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzhg) zzb).zza(new zzgg(taskCompletionSource), this.zzlsw);
    }
}
