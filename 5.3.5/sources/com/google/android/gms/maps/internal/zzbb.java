package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.maps.model.PointOfInterest;

@Hide
public interface zzbb extends IInterface {
    void zza(PointOfInterest pointOfInterest) throws RemoteException;
}
