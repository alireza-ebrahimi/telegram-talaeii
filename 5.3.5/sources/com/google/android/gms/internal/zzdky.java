package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.vision.barcode.Barcode;

@Hide
public interface zzdky extends IInterface {
    Barcode[] zza(IObjectWrapper iObjectWrapper, zzdld zzdld) throws RemoteException;

    Barcode[] zzb(IObjectWrapper iObjectWrapper, zzdld zzdld) throws RemoteException;

    void zzblm() throws RemoteException;
}
