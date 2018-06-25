package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcfy;
import com.google.android.gms.internal.zzcgn;
import com.google.android.gms.internal.zzchh;
import com.google.android.gms.internal.zzchs;

public class LocationServices {
    public static final Api<NoOptions> API = new Api("LocationServices.API", zzegv, zzegu);
    @Deprecated
    public static final FusedLocationProviderApi FusedLocationApi = new zzcfy();
    @Deprecated
    public static final GeofencingApi GeofencingApi = new zzcgn();
    @Deprecated
    public static final SettingsApi SettingsApi = new zzchs();
    private static final zzf<zzchh> zzegu = new zzf();
    private static final com.google.android.gms.common.api.Api.zza<zzchh, NoOptions> zzegv = new zzad();

    @Hide
    public static abstract class zza<R extends Result> extends zzm<R, zzchh> {
        public zza(GoogleApiClient googleApiClient) {
            super(LocationServices.API, googleApiClient);
        }

        @Hide
        public final /* bridge */ /* synthetic */ void setResult(Object obj) {
            super.setResult((Result) obj);
        }
    }

    private LocationServices() {
    }

    public static FusedLocationProviderClient getFusedLocationProviderClient(@NonNull Activity activity) {
        return new FusedLocationProviderClient(activity);
    }

    public static FusedLocationProviderClient getFusedLocationProviderClient(@NonNull Context context) {
        return new FusedLocationProviderClient(context);
    }

    public static GeofencingClient getGeofencingClient(@NonNull Activity activity) {
        return new GeofencingClient(activity);
    }

    public static GeofencingClient getGeofencingClient(@NonNull Context context) {
        return new GeofencingClient(context);
    }

    public static SettingsClient getSettingsClient(@NonNull Activity activity) {
        return new SettingsClient(activity);
    }

    public static SettingsClient getSettingsClient(@NonNull Context context) {
        return new SettingsClient(context);
    }

    @Hide
    public static zzchh zzi(GoogleApiClient googleApiClient) {
        boolean z = true;
        zzbq.checkArgument(googleApiClient != null, "GoogleApiClient parameter is required.");
        zzchh zzchh = (zzchh) googleApiClient.zza(zzegu);
        if (zzchh == null) {
            z = false;
        }
        zzbq.zza(z, (Object) "GoogleApiClient is not configured to use the LocationServices.API Api. Pass thisinto GoogleApiClient.Builder#addApi() to use this feature.");
        return zzchh;
    }
}
