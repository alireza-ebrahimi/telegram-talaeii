package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzc extends zzev implements zza {
    zzc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
    }

    public final IObjectWrapper zzaxx() throws RemoteException {
        Parcel zza = zza(4, zzbc());
        IObjectWrapper zzaq = zza.zzaq(zza.readStrongBinder());
        zza.recycle();
        return zzaq;
    }

    public final IObjectWrapper zzd(Bitmap bitmap) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) bitmap);
        zzbc = zza(6, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper zze(float f) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeFloat(f);
        zzbc = zza(5, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper zzeo(int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeInt(i);
        zzbc = zza(1, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper zziu(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzbc = zza(2, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper zziv(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzbc = zza(3, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }

    public final IObjectWrapper zziw(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzbc = zza(7, zzbc);
        IObjectWrapper zzaq = zza.zzaq(zzbc.readStrongBinder());
        zzbc.recycle();
        return zzaq;
    }
}
