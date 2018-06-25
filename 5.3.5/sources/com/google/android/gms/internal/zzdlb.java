package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzdlb extends zzev implements zzdla {
    zzdlb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetectorCreator");
    }

    public final zzdky zza(IObjectWrapper iObjectWrapper, zzdkv zzdkv) throws RemoteException {
        zzdky zzdky;
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzex.zza(zzbc, (Parcelable) zzdkv);
        Parcel zza = zza(1, zzbc);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzdky = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetector");
            zzdky = queryLocalInterface instanceof zzdky ? (zzdky) queryLocalInterface : new zzdkz(readStrongBinder);
        }
        zza.recycle();
        return zzdky;
    }
}
