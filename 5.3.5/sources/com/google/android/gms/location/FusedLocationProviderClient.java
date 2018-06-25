package com.google.android.gms.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.api.internal.zzdf;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.internal.zzcgl;
import com.google.android.gms.internal.zzcgr;
import com.google.android.gms.internal.zzcgs;
import com.google.android.gms.internal.zzchl;
import com.google.android.gms.internal.zzchz;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class FusedLocationProviderClient extends GoogleApi<NoOptions> {
    public static final String KEY_VERTICAL_ACCURACY = "verticalAccuracy";

    static class zza extends zzcgs {
        private final TaskCompletionSource<Void> zzejm;

        public zza(TaskCompletionSource<Void> taskCompletionSource) {
            this.zzejm = taskCompletionSource;
        }

        public final void zza(zzcgl zzcgl) {
            zzdf.zza(zzcgl.getStatus(), null, this.zzejm);
        }
    }

    @Hide
    public FusedLocationProviderClient(@NonNull Activity activity) {
        super(activity, LocationServices.API, null, new zzg());
    }

    @Hide
    public FusedLocationProviderClient(@NonNull Context context) {
        super(context, LocationServices.API, null, new zzg());
    }

    private final zzcgr zzc(TaskCompletionSource<Boolean> taskCompletionSource) {
        return new zzp(this, taskCompletionSource);
    }

    public Task<Void> flushLocations() {
        return zzbj.zzb(LocationServices.FusedLocationApi.flushLocations(zzahw()));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Location> getLastLocation() {
        return zza(new zzl(this));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<LocationAvailability> getLocationAvailability() {
        return zza(new zzm(this));
    }

    public Task<Void> removeLocationUpdates(PendingIntent pendingIntent) {
        return zzbj.zzb(LocationServices.FusedLocationApi.removeLocationUpdates(zzahw(), pendingIntent));
    }

    public Task<Void> removeLocationUpdates(LocationCallback locationCallback) {
        return zzdf.zza(zza(zzcm.zzb(locationCallback, LocationCallback.class.getSimpleName())));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Void> requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        return zzbj.zzb(LocationServices.FusedLocationApi.requestLocationUpdates(zzahw(), locationRequest, pendingIntent));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Void> requestLocationUpdates(LocationRequest locationRequest, LocationCallback locationCallback, @Nullable Looper looper) {
        zzchl zza = zzchl.zza(locationRequest);
        zzci zzb = zzcm.zzb(locationCallback, zzchz.zzb(looper), LocationCallback.class.getSimpleName());
        return zza(new zzn(this, zzb, zza, zzb), new zzo(this, zzb.zzakx()));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Void> setMockLocation(Location location) {
        return zzbj.zzb(LocationServices.FusedLocationApi.setMockLocation(zzahw(), location));
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public Task<Void> setMockMode(boolean z) {
        return zzbj.zzb(LocationServices.FusedLocationApi.setMockMode(zzahw(), z));
    }
}
