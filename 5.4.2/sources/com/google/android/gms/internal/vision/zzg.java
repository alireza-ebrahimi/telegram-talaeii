package com.google.android.gms.internal.vision;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.vision.barcode.Barcode;

public final class zzg extends zza implements zzf {
    zzg(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetector");
    }

    public final Barcode[] zza(IObjectWrapper iObjectWrapper, zzk zzk) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzb.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzb.zza(obtainAndWriteInterfaceToken, (Parcelable) zzk);
        Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken);
        Barcode[] barcodeArr = (Barcode[]) transactAndReadException.createTypedArray(Barcode.CREATOR);
        transactAndReadException.recycle();
        return barcodeArr;
    }

    public final Barcode[] zzb(IObjectWrapper iObjectWrapper, zzk zzk) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzb.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzb.zza(obtainAndWriteInterfaceToken, (Parcelable) zzk);
        Parcel transactAndReadException = transactAndReadException(2, obtainAndWriteInterfaceToken);
        Barcode[] barcodeArr = (Barcode[]) transactAndReadException.createTypedArray(Barcode.CREATOR);
        transactAndReadException.recycle();
        return barcodeArr;
    }

    public final void zzf() {
        transactAndReadExceptionReturnVoid(3, obtainAndWriteInterfaceToken());
    }
}
