package com.google.android.gms.internal.maps;

import android.os.IBinder;
import android.os.IInterface;

public abstract class zzad extends zzb implements zzac {
    public static zzac zzj(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
        return queryLocalInterface instanceof zzac ? (zzac) queryLocalInterface : new zzae(iBinder);
    }
}
