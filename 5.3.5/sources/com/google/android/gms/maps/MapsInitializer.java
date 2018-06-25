package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.internal.zze;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class MapsInitializer {
    private static boolean initialized = false;

    private MapsInitializer() {
    }

    public static synchronized int initialize(Context context) {
        int i = 0;
        synchronized (MapsInitializer.class) {
            zzbq.checkNotNull(context, "Context is null");
            if (!initialized) {
                try {
                    zze zzdz = zzbz.zzdz(context);
                    CameraUpdateFactory.zza(zzdz.zzaxt());
                    BitmapDescriptorFactory.zza(zzdz.zzaxu());
                    initialized = true;
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
