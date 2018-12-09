package com.google.android.gms.identity.intents;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.identity.intents.Address.AddressOptions;
import com.google.android.gms.internal.identity.zze;

final class zza extends AbstractClientBuilder<zze, AddressOptions> {
    zza() {
    }

    public final /* synthetic */ Client buildClient(Context context, Looper looper, ClientSettings clientSettings, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        AddressOptions addressOptions = (AddressOptions) obj;
        Preconditions.checkArgument(context instanceof Activity, "An Activity must be used for Address APIs");
        if (addressOptions == null) {
            addressOptions = new AddressOptions();
        }
        return new zze((Activity) context, looper, clientSettings, addressOptions.theme, connectionCallbacks, onConnectionFailedListener);
    }
}
