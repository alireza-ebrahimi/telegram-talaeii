package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.location.LocationSettingsResult;

final class zzchk extends zzcgz {
    private zzn<LocationSettingsResult> zziul;

    public zzchk(zzn<LocationSettingsResult> zzn) {
        zzbq.checkArgument(zzn != null, "listener can't be null.");
        this.zziul = zzn;
    }

    public final void zza(LocationSettingsResult locationSettingsResult) throws RemoteException {
        this.zziul.setResult(locationSettingsResult);
        this.zziul = null;
    }
}
