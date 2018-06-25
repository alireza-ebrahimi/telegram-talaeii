package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzr;

final class zzcyh extends zza<zzcyt, zzcyk> {
    zzcyh() {
    }

    public final /* synthetic */ zze zza(Context context, Looper looper, zzr zzr, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        zzcyk zzcyk = (zzcyk) obj;
        return new zzcyt(context, looper, true, zzr, zzcyk == null ? zzcyk.zzklp : zzcyk, connectionCallbacks, onConnectionFailedListener);
    }
}
