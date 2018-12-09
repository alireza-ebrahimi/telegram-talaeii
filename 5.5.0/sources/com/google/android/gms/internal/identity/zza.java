package com.google.android.gms.internal.identity;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public class zza implements IInterface {
    private final IBinder zza;
    private final String zzb;

    protected zza(IBinder iBinder, String str) {
        this.zza = iBinder;
        this.zzb = str;
    }

    public IBinder asBinder() {
        return this.zza;
    }

    protected final Parcel obtainAndWriteInterfaceToken() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken(this.zzb);
        return obtain;
    }

    protected final void transactAndReadExceptionReturnVoid(int i, Parcel parcel) {
        Parcel obtain = Parcel.obtain();
        try {
            this.zza.transact(2, parcel, obtain, 0);
            obtain.readException();
        } finally {
            parcel.recycle();
            obtain.recycle();
        }
    }
}
