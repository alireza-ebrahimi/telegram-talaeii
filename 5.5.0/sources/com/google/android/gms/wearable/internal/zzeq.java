package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.internal.wearable.zza;
import com.google.android.gms.internal.wearable.zzc;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.PutDataRequest;

public final class zzeq extends zza implements zzep {
    zzeq(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wearable.internal.IWearableService");
    }

    public final void zza(zzek zzek) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        transactAndReadExceptionReturnVoid(8, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, int i) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(43, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, Uri uri) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) uri);
        transactAndReadExceptionReturnVoid(7, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, Uri uri, int i) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) uri);
        obtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(40, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, Asset asset) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) asset);
        transactAndReadExceptionReturnVoid(13, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, PutDataRequest putDataRequest) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) putDataRequest);
        transactAndReadExceptionReturnVoid(6, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, zzd zzd) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzd);
        transactAndReadExceptionReturnVoid(16, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, zzei zzei, String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzei);
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(34, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, zzfw zzfw) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) zzfw);
        transactAndReadExceptionReturnVoid(17, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(46, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, String str, int i) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(42, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, String str, ParcelFileDescriptor parcelFileDescriptor) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) parcelFileDescriptor);
        transactAndReadExceptionReturnVoid(38, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, String str, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) parcelFileDescriptor);
        obtainAndWriteInterfaceToken.writeLong(j);
        obtainAndWriteInterfaceToken.writeLong(j2);
        transactAndReadExceptionReturnVoid(39, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, String str, String str2) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        transactAndReadExceptionReturnVoid(31, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzek zzek, String str, String str2, byte[] bArr) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        obtainAndWriteInterfaceToken.writeByteArray(bArr);
        transactAndReadExceptionReturnVoid(12, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzek zzek) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        transactAndReadExceptionReturnVoid(14, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzek zzek, Uri uri, int i) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) uri);
        obtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(41, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzek zzek, zzei zzei, String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzei);
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(35, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzek zzek, String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(47, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzek zzek, String str, int i) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeInt(i);
        transactAndReadExceptionReturnVoid(33, obtainAndWriteInterfaceToken);
    }

    public final void zzc(zzek zzek) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        transactAndReadExceptionReturnVoid(15, obtainAndWriteInterfaceToken);
    }

    public final void zzc(zzek zzek, String str) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) zzek);
        obtainAndWriteInterfaceToken.writeString(str);
        transactAndReadExceptionReturnVoid(32, obtainAndWriteInterfaceToken);
    }
}
