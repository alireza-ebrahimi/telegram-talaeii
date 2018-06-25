package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

final class zzr {
    private final Messenger zzinb;
    private final zzi zzolc;

    zzr(IBinder iBinder) throws RemoteException {
        String interfaceDescriptor = iBinder.getInterfaceDescriptor();
        if ("android.os.IMessenger".equals(interfaceDescriptor)) {
            this.zzinb = new Messenger(iBinder);
            this.zzolc = null;
        } else if ("com.google.android.gms.iid.IMessengerCompat".equals(interfaceDescriptor)) {
            this.zzolc = new zzi(iBinder);
            this.zzinb = null;
        } else {
            String str = "MessengerIpcClient";
            String str2 = "Invalid interface descriptor: ";
            interfaceDescriptor = String.valueOf(interfaceDescriptor);
            Log.w(str, interfaceDescriptor.length() != 0 ? str2.concat(interfaceDescriptor) : new String(str2));
            throw new RemoteException();
        }
    }

    final void send(Message message) throws RemoteException {
        if (this.zzinb != null) {
            this.zzinb.send(message);
        } else if (this.zzolc != null) {
            this.zzolc.send(message);
        } else {
            throw new IllegalStateException("Both messengers are null");
        }
    }
}
