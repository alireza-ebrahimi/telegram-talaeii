package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener;

final class zzaf extends zzcq<zzhg, OnCapabilityChangedListener> {
    private zzci<CapabilityListener> zzgwf;
    private IntentFilter[] zzlsb;
    private OnCapabilityChangedListener zzlsw;

    private zzaf(OnCapabilityChangedListener onCapabilityChangedListener, IntentFilter[] intentFilterArr, zzci<OnCapabilityChangedListener> zzci) {
        super(zzci);
        this.zzlsw = onCapabilityChangedListener;
        this.zzlsb = intentFilterArr;
        this.zzgwf = zzci;
    }

    protected final /* synthetic */ void zzb(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzhg) zzb).zza(new zzgh(taskCompletionSource), this.zzlsw, this.zzgwf, this.zzlsb);
    }
}
