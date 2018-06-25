package com.google.android.gms.vision.face.internal.client;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzdld;

@Hide
public interface zze extends IInterface {
    void zzblm() throws RemoteException;

    FaceParcel[] zzc(IObjectWrapper iObjectWrapper, zzdld zzdld) throws RemoteException;

    boolean zzfo(int i) throws RemoteException;
}
