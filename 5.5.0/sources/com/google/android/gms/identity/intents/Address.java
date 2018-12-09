package com.google.android.gms.identity.intents;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.internal.identity.zze;

public final class Address {
    public static final Api<AddressOptions> API = new Api("Address.API", CLIENT_BUILDER, CLIENT_KEY);
    private static final AbstractClientBuilder<zze, AddressOptions> CLIENT_BUILDER = new zza();
    private static final ClientKey<zze> CLIENT_KEY = new ClientKey();

    public static final class AddressOptions implements HasOptions {
        public final int theme;

        public AddressOptions() {
            this.theme = 0;
        }

        public AddressOptions(int i) {
            this.theme = i;
        }
    }

    private static abstract class zza extends ApiMethodImpl<Status, zze> {
        public zza(GoogleApiClient googleApiClient) {
            super(Address.API, googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status status) {
            return status;
        }
    }

    public static void requestUserAddress(GoogleApiClient googleApiClient, UserAddressRequest userAddressRequest, int i) {
        googleApiClient.enqueue(new zzb(googleApiClient, userAddressRequest, i));
    }
}
