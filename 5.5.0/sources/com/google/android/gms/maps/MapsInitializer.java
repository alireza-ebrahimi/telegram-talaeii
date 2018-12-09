package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.internal.zze;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import javax.annotation.concurrent.GuardedBy;

public final class MapsInitializer {
    @GuardedBy("MapsInitializer.class")
    private static boolean zzbl = false;

    private MapsInitializer() {
    }

    public static synchronized int initialize(Context context) {
        int i = 0;
        synchronized (MapsInitializer.class) {
            Preconditions.checkNotNull(context, "Context is null");
            if (!zzbl) {
                try {
                    zze zza = zzbz.zza(context);
                    CameraUpdateFactory.zza(zza.zzd());
                    BitmapDescriptorFactory.zza(zza.zze());
                    zzbl = true;
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                    i = e2.errorCode;
                }
            }
        }
        return i;
    }
}
