package com.google.android.gms.internal.measurement;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public final class zzfb extends zzn implements zzez {
    zzfb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    public final List<zzjz> zza(zzdz zzdz, boolean z) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        zzp.writeBoolean(obtainAndWriteInterfaceToken, z);
        obtainAndWriteInterfaceToken = transactAndReadException(7, obtainAndWriteInterfaceToken);
        List createTypedArrayList = obtainAndWriteInterfaceToken.createTypedArrayList(zzjz.CREATOR);
        obtainAndWriteInterfaceToken.recycle();
        return createTypedArrayList;
    }

    public final List<zzee> zza(String str, String str2, zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        obtainAndWriteInterfaceToken = transactAndReadException(16, obtainAndWriteInterfaceToken);
        List createTypedArrayList = obtainAndWriteInterfaceToken.createTypedArrayList(zzee.CREATOR);
        obtainAndWriteInterfaceToken.recycle();
        return createTypedArrayList;
    }

    public final List<zzjz> zza(String str, String str2, String str3, boolean z) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        obtainAndWriteInterfaceToken.writeString(str3);
        zzp.writeBoolean(obtainAndWriteInterfaceToken, z);
        obtainAndWriteInterfaceToken = transactAndReadException(15, obtainAndWriteInterfaceToken);
        List createTypedArrayList = obtainAndWriteInterfaceToken.createTypedArrayList(zzjz.CREATOR);
        obtainAndWriteInterfaceToken.recycle();
        return createTypedArrayList;
    }

    public final List<zzjz> zza(String str, String str2, boolean z, zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zzp.writeBoolean(obtainAndWriteInterfaceToken, z);
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        obtainAndWriteInterfaceToken = transactAndReadException(14, obtainAndWriteInterfaceToken);
        List createTypedArrayList = obtainAndWriteInterfaceToken.createTypedArrayList(zzjz.CREATOR);
        obtainAndWriteInterfaceToken.recycle();
        return createTypedArrayList;
    }

    public final void zza(long j, String str, String str2, String str3) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeLong(j);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        obtainAndWriteInterfaceToken.writeString(str3);
        transactAndReadExceptionReturnVoid(10, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        transactAndReadExceptionReturnVoid(4, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzee zzee, zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzee);
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        transactAndReadExceptionReturnVoid(12, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzew zzew, zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzew);
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzew zzew, String str, String str2) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzew);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        transactAndReadExceptionReturnVoid(5, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzjz zzjz, zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzjz);
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        transactAndReadExceptionReturnVoid(2, obtainAndWriteInterfaceToken);
    }

    public final byte[] zza(zzew zzew, String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzew);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken = transactAndReadException(9, obtainAndWriteInterfaceToken);
        byte[] createByteArray = obtainAndWriteInterfaceToken.createByteArray();
        obtainAndWriteInterfaceToken.recycle();
        return createByteArray;
    }

    public final void zzb(zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzee zzee) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzee);
        transactAndReadExceptionReturnVoid(13, obtainAndWriteInterfaceToken);
    }

    public final String zzc(zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        obtainAndWriteInterfaceToken = transactAndReadException(11, obtainAndWriteInterfaceToken);
        String readString = obtainAndWriteInterfaceToken.readString();
        obtainAndWriteInterfaceToken.recycle();
        return readString;
    }

    public final void zzd(zzdz zzdz) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzp.zza(obtainAndWriteInterfaceToken, (Parcelable) zzdz);
        transactAndReadExceptionReturnVoid(18, obtainAndWriteInterfaceToken);
    }

    public final List<zzee> zze(String str, String str2, String str3) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        obtainAndWriteInterfaceToken.writeString(str3);
        obtainAndWriteInterfaceToken = transactAndReadException(17, obtainAndWriteInterfaceToken);
        List createTypedArrayList = obtainAndWriteInterfaceToken.createTypedArrayList(zzee.CREATOR);
        obtainAndWriteInterfaceToken.recycle();
        return createTypedArrayList;
    }
}
