package com.google.android.gms.identity.intents;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzcdo;

public final class Address {
    public static final Api<AddressOptions> API = new Api("Address.API", zzegv, zzegu);
    private static zzf<zzcdo> zzegu = new zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzcdo, AddressOptions> zzegv = new zza();

    public static final class AddressOptions implements HasOptions {
        public final int theme;

        public AddressOptions() {
            this.theme = 0;
        }

        public AddressOptions(int i) {
            this.theme = i;
        }
    }

    static abstract class zza extends zzm<Status, zzcdo> {
        public zza(GoogleApiClient googleApiClient) {
            super(Address.API, googleApiClient);
        }

        @Hide
        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Status) obj);
        }

        public final /* synthetic */ Result zzb(Status status) {
            return status;
        }
    }

    public static void requestUserAddress(GoogleApiClient googleApiClient, UserAddressRequest userAddressRequest, int i) {
        googleApiClient.zzd(new zzb(googleApiClient, userAddressRequest, i));
    }
}
