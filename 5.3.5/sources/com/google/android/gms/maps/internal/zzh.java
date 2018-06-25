package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.maps.model.internal.zzp;

@Hide
public interface zzh extends IInterface {
    IObjectWrapper zzh(zzp zzp) throws RemoteException;

    IObjectWrapper zzi(zzp zzp) throws RemoteException;
}
