package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/* renamed from: com.google.firebase.iid.d */
final class C1933d {
    /* renamed from: a */
    private final Messenger f5718a;
    /* renamed from: b */
    private final zzi f5719b;

    C1933d(IBinder iBinder) {
        String interfaceDescriptor = iBinder.getInterfaceDescriptor();
        if ("android.os.IMessenger".equals(interfaceDescriptor)) {
            this.f5718a = new Messenger(iBinder);
            this.f5719b = null;
        } else if ("com.google.android.gms.iid.IMessengerCompat".equals(interfaceDescriptor)) {
            this.f5719b = new zzi(iBinder);
            this.f5718a = null;
        } else {
            String str = "MessengerIpcClient";
            String str2 = "Invalid interface descriptor: ";
            interfaceDescriptor = String.valueOf(interfaceDescriptor);
            Log.w(str, interfaceDescriptor.length() != 0 ? str2.concat(interfaceDescriptor) : new String(str2));
            throw new RemoteException();
        }
    }

    /* renamed from: a */
    final void m8840a(Message message) {
        if (this.f5718a != null) {
            this.f5718a.send(message);
        } else if (this.f5719b != null) {
            this.f5719b.m8899a(message);
        } else {
            throw new IllegalStateException("Both messengers are null");
        }
    }
}
