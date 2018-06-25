package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;

@Hide
public interface zzfp extends IInterface {
    String getId() throws RemoteException;

    boolean zzb(boolean z) throws RemoteException;

    boolean zzbn() throws RemoteException;
}
