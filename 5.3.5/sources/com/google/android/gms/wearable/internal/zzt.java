package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;

final class zzt implements zzc<CapabilityListener> {
    private /* synthetic */ IntentFilter[] zzlso;

    zzt(IntentFilter[] intentFilterArr) {
        this.zzlso = intentFilterArr;
    }

    public final /* synthetic */ void zza(zzhg zzhg, zzn zzn, Object obj, zzci zzci) throws RemoteException {
        zzhg.zza(zzn, (CapabilityListener) obj, zzci, this.zzlso);
    }
}
