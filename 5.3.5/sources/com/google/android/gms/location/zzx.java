package com.google.android.gms.location;

import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;

@Hide
public interface zzx extends IInterface {
    void onLocationChanged(Location location) throws RemoteException;
}
