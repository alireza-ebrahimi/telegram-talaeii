package com.google.android.gms.identity.intents;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.identity.intents.Address.AddressOptions;
import com.google.android.gms.internal.zzcdo;

final class zza extends com.google.android.gms.common.api.Api.zza<zzcdo, AddressOptions> {
    zza() {
    }

    public final /* synthetic */ zze zza(Context context, Looper looper, zzr zzr, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        AddressOptions addressOptions = (AddressOptions) obj;
        zzbq.checkArgument(context instanceof Activity, "An Activity must be used for Address APIs");
        if (addressOptions == null) {
            addressOptions = new AddressOptions();
        }
        return new zzcdo((Activity) context, looper, zzr, addressOptions.theme, connectionCallbacks, onConnectionFailedListener);
    }
}
