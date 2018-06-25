package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

public final class zzcjd extends zzev implements zzcjb {
    zzcjd(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    public final List<zzcnl> zza(zzcif zzcif, boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzex.zza(zzbc, z);
        zzbc = zza(7, zzbc);
        List createTypedArrayList = zzbc.createTypedArrayList(zzcnl.CREATOR);
        zzbc.recycle();
        return createTypedArrayList;
    }

    public final List<zzcii> zza(String str, String str2, zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzbc = zza(16, zzbc);
        List createTypedArrayList = zzbc.createTypedArrayList(zzcii.CREATOR);
        zzbc.recycle();
        return createTypedArrayList;
    }

    public final List<zzcnl> zza(String str, String str2, String str3, boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzbc.writeString(str3);
        zzex.zza(zzbc, z);
        zzbc = zza(15, zzbc);
        List createTypedArrayList = zzbc.createTypedArrayList(zzcnl.CREATOR);
        zzbc.recycle();
        return createTypedArrayList;
    }

    public final List<zzcnl> zza(String str, String str2, boolean z, zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzex.zza(zzbc, z);
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzbc = zza(14, zzbc);
        List createTypedArrayList = zzbc.createTypedArrayList(zzcnl.CREATOR);
        zzbc.recycle();
        return createTypedArrayList;
    }

    public final void zza(long j, String str, String str2, String str3) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeLong(j);
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzbc.writeString(str3);
        zzb(10, zzbc);
    }

    public final void zza(zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzb(4, zzbc);
    }

    public final void zza(zzcii zzcii, zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcii);
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzb(12, zzbc);
    }

    public final void zza(zzcix zzcix, zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcix);
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzb(1, zzbc);
    }

    public final void zza(zzcix zzcix, String str, String str2) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcix);
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzb(5, zzbc);
    }

    public final void zza(zzcnl zzcnl, zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcnl);
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzb(2, zzbc);
    }

    public final byte[] zza(zzcix zzcix, String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcix);
        zzbc.writeString(str);
        zzbc = zza(9, zzbc);
        byte[] createByteArray = zzbc.createByteArray();
        zzbc.recycle();
        return createByteArray;
    }

    public final void zzb(zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzb(6, zzbc);
    }

    public final void zzb(zzcii zzcii) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcii);
        zzb(13, zzbc);
    }

    public final String zzc(zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzbc = zza(11, zzbc);
        String readString = zzbc.readString();
        zzbc.recycle();
        return readString;
    }

    public final void zzd(zzcif zzcif) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcif);
        zzb(18, zzbc);
    }

    public final List<zzcii> zzk(String str, String str2, String str3) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzbc.writeString(str3);
        zzbc = zza(17, zzbc);
        List createTypedArrayList = zzbc.createTypedArrayList(zzcii.CREATOR);
        zzbc.recycle();
        return createTypedArrayList;
    }
}
