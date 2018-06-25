package com.google.firebase.auth.p104a.p105a;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;

/* renamed from: com.google.firebase.auth.a.a.t */
final class C1846t extends AbstractClientBuilder<C1840k, C1847u> {
    C1846t() {
    }

    public final /* synthetic */ Client buildClient(Context context, Looper looper, ClientSettings clientSettings, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        return new C1841l(context, looper, clientSettings, (C1847u) obj, connectionCallbacks, onConnectionFailedListener);
    }
}
