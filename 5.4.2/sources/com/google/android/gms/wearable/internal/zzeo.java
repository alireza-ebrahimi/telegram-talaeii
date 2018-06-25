package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.internal.wearable.zza;
import com.google.android.gms.internal.wearable.zzc;
import java.util.List;

public final class zzeo extends zza implements zzem {
    zzeo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wearable.internal.IWearableListener");
    }

    public final void onConnectedNodes(List<zzfo> list) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeTypedList(list);
        transactOneway(5, obtainAndWriteInterfaceToken);
    }

    public final void zza(DataHolder dataHolder) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) dataHolder);
        transactOneway(1, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzah zzah) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzah);
        transactOneway(8, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzaw zzaw) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzaw);
        transactOneway(7, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzfe zzfe) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzfe);
        transactOneway(2, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzfo zzfo) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzfo);
        transactOneway(3, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzi zzi) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzi);
        transactOneway(9, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzl zzl) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzl);
        transactOneway(6, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzfo zzfo) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzfo);
        transactOneway(4, obtainAndWriteInterfaceToken);
    }
}
