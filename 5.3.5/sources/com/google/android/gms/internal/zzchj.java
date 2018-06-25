package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.location.LocationStatusCodes;

final class zzchj extends zzcgv {
    private zzn<Status> zziul;

    public zzchj(zzn<Status> zzn) {
        this.zziul = zzn;
    }

    private final void zzel(int i) {
        if (this.zziul == null) {
            Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
            return;
        }
        this.zziul.setResult(LocationStatusCodes.zzek(LocationStatusCodes.zzej(i)));
        this.zziul = null;
    }

    public final void zza(int i, PendingIntent pendingIntent) {
        zzel(i);
    }

    public final void zza(int i, String[] strArr) {
        Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
    }

    public final void zzb(int i, String[] strArr) {
        zzel(i);
    }
}
