package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;

@Hide
public interface zzaz extends IInterface {
    void onMyLocationClick(Location location) throws RemoteException;
}
