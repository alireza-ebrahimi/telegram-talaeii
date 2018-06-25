package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class zzdli extends zzev implements zzdlh {
    zzdli(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.text.internal.client.INativeTextRecognizer");
    }

    public final zzdll[] zza(IObjectWrapper iObjectWrapper, zzdld zzdld, zzdln zzdln) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) iObjectWrapper);
        zzex.zza(zzbc, (Parcelable) zzdld);
        zzex.zza(zzbc, (Parcelable) zzdln);
        Parcel zza = zza(3, zzbc);
        zzdll[] zzdllArr = (zzdll[]) zza.createTypedArray(zzdll.CREATOR);
        zza.recycle();
        return zzdllArr;
    }

    public final void zzblp() throws RemoteException {
        zzb(2, zzbc());
    }
}
