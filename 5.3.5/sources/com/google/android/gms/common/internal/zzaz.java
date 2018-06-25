package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

final class zzaz implements zzay {
    private final IBinder zzala;

    zzaz(IBinder iBinder) {
        this.zzala = iBinder;
    }

    public final IBinder asBinder() {
        return this.zzala;
    }

    public final void zza(zzaw zzaw, zzz zzz) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
            obtain.writeStrongBinder(zzaw.asBinder());
            obtain.writeInt(1);
            zzz.writeToParcel(obtain, 0);
            this.zzala.transact(46, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
