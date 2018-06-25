package com.google.android.gms.location;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.tasks.Task;

public class SettingsClient extends GoogleApi<NoOptions> {
    @Hide
    public SettingsClient(@NonNull Activity activity) {
        super(activity, LocationServices.API, null, new zzg());
    }

    @Hide
    public SettingsClient(@NonNull Context context) {
        super(context, LocationServices.API, null, new zzg());
    }

    public Task<LocationSettingsResponse> checkLocationSettings(LocationSettingsRequest locationSettingsRequest) {
        return zzbj.zza(LocationServices.SettingsApi.checkLocationSettings(zzahw(), locationSettingsRequest), new LocationSettingsResponse());
    }
}
