package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcq;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.DataClient.OnDataChangedListener;

final class zzcv extends zzcq<zzhg, OnDataChangedListener> {
    private zzci<DataListener> zzgwf;
    private IntentFilter[] zzlsb;
    private OnDataChangedListener zzlue;

    private zzcv(OnDataChangedListener onDataChangedListener, IntentFilter[] intentFilterArr, zzci<OnDataChangedListener> zzci) {
        super(zzci);
        this.zzlue = onDataChangedListener;
        this.zzlsb = intentFilterArr;
        this.zzgwf = zzci;
    }

    protected final /* synthetic */ void zzb(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzhg) zzb).zza(new zzgh(taskCompletionSource), this.zzlue, this.zzgwf, this.zzlsb);
    }
}
