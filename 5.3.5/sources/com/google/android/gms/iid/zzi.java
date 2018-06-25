package com.google.android.gms.iid;

import android.os.IInterface;
import android.os.Message;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;

@Hide
public interface zzi extends IInterface {
    void send(Message message) throws RemoteException;
}
