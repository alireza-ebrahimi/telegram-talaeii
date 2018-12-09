package com.google.android.gms.internal.maps;

import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.Stub;

public final class zzg extends zza implements zze {
    zzg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
    }

    public final IObjectWrapper zza(float f) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeFloat(f);
        obtainAndWriteInterfaceToken = transactAndReadException(5, obtainAndWriteInterfaceToken);
        IObjectWrapper asInterface = Stub.asInterface(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return asInterface;
    }

    public final IObjectWrapper zza(int i) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeInt(i);
        obtainAndWriteInterfaceToken = transactAndReadException(1, obtainAndWriteInterfaceToken);
        IObjectWrapper asInterface = Stub.asInterface(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return asInterface;
    }

    public final IObjectWrapper zza(Bitmap bitmap) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bitmap);
        obtainAndWriteInterfaceToken = transactAndReadException(6, obtainAndWriteInterfaceToken);
        IObjectWrapper asInterface = Stub.asInterface(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return asInterface;
    }

    public final IObjectWrapper zza(String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken = transactAndReadException(2, obtainAndWriteInterfaceToken);
        IObjectWrapper asInterface = Stub.asInterface(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return asInterface;
    }

    public final IObjectWrapper zzb(String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken = transactAndReadException(3, obtainAndWriteInterfaceToken);
        IObjectWrapper asInterface = Stub.asInterface(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return asInterface;
    }

    public final IObjectWrapper zzc(String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken = transactAndReadException(7, obtainAndWriteInterfaceToken);
        IObjectWrapper asInterface = Stub.asInterface(obtainAndWriteInterfaceToken.readStrongBinder());
        obtainAndWriteInterfaceToken.recycle();
        return asInterface;
    }

    public final IObjectWrapper zzh() {
        Parcel transactAndReadException = transactAndReadException(4, obtainAndWriteInterfaceToken());
        IObjectWrapper asInterface = Stub.asInterface(transactAndReadException.readStrongBinder());
        transactAndReadException.recycle();
        return asInterface;
    }
}
