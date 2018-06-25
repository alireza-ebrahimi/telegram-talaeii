package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzdo;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.DataClient.OnDataChangedListener;

final class zzcw extends zzdo<zzhg, OnDataChangedListener> {
    private final OnDataChangedListener zzlue;

    private zzcw(OnDataChangedListener onDataChangedListener, zzck<OnDataChangedListener> zzck) {
        super(zzck);
        this.zzlue = onDataChangedListener;
    }

    protected final /* synthetic */ void zzc(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzhg) zzb).zza(new zzgg(taskCompletionSource), this.zzlue);
    }
}
