package com.google.android.gms.maps.model.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.maps.model.Tile;

@Hide
public interface zzz extends IInterface {
    Tile getTile(int i, int i2, int i3) throws RemoteException;
}
