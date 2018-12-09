package com.google.android.gms.vision.face.internal.client;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.vision.zza;
import com.google.android.gms.internal.vision.zzb;

public final class zzh extends zza implements zzg {
    zzh(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.face.internal.client.INativeFaceDetectorCreator");
    }

    public final zze zza(IObjectWrapper iObjectWrapper, zzc zzc) {
        zze zze;
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzb.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzb.zza(obtainAndWriteInterfaceToken, (Parcelable) zzc);
        Parcel transactAndReadException = transactAndReadException(1, obtainAndWriteInterfaceToken);
        IBinder readStrongBinder = transactAndReadException.readStrongBinder();
        if (readStrongBinder == null) {
            zze = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.vision.face.internal.client.INativeFaceDetector");
            zze = queryLocalInterface instanceof zze ? (zze) queryLocalInterface : new zzf(readStrongBinder);
        }
        transactAndReadException.recycle();
        return zze;
    }
}
