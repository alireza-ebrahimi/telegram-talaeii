package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.PutDataRequest;

public final class zzeq extends zzev implements zzep {
    zzeq(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.wearable.internal.IWearableService");
    }

    public final void zza(zzek zzek) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzb(8, zzbc);
    }

    public final void zza(zzek zzek, int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeInt(i);
        zzb(43, zzbc);
    }

    public final void zza(zzek zzek, Uri uri) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (Parcelable) uri);
        zzb(7, zzbc);
    }

    public final void zza(zzek zzek, Uri uri, int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (Parcelable) uri);
        zzbc.writeInt(i);
        zzb(40, zzbc);
    }

    public final void zza(zzek zzek, Asset asset) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (Parcelable) asset);
        zzb(13, zzbc);
    }

    public final void zza(zzek zzek, PutDataRequest putDataRequest) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (Parcelable) putDataRequest);
        zzb(6, zzbc);
    }

    public final void zza(zzek zzek, zzd zzd) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (Parcelable) zzd);
        zzb(16, zzbc);
    }

    public final void zza(zzek zzek, zzei zzei, String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (IInterface) zzei);
        zzbc.writeString(str);
        zzb(34, zzbc);
    }

    public final void zza(zzek zzek, zzfw zzfw) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (Parcelable) zzfw);
        zzb(17, zzbc);
    }

    public final void zza(zzek zzek, String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzb(46, zzbc);
    }

    public final void zza(zzek zzek, String str, int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzbc.writeInt(i);
        zzb(42, zzbc);
    }

    public final void zza(zzek zzek, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzex.zza(zzbc, (Parcelable) parcelFileDescriptor);
        zzb(38, zzbc);
    }

    public final void zza(zzek zzek, String str, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzex.zza(zzbc, (Parcelable) parcelFileDescriptor);
        zzbc.writeLong(j);
        zzbc.writeLong(j2);
        zzb(39, zzbc);
    }

    public final void zza(zzek zzek, String str, String str2) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzb(31, zzbc);
    }

    public final void zza(zzek zzek, String str, String str2, byte[] bArr) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzbc.writeString(str2);
        zzbc.writeByteArray(bArr);
        zzb(12, zzbc);
    }

    public final void zzb(zzek zzek) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzb(14, zzbc);
    }

    public final void zzb(zzek zzek, Uri uri, int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (Parcelable) uri);
        zzbc.writeInt(i);
        zzb(41, zzbc);
    }

    public final void zzb(zzek zzek, zzei zzei, String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzex.zza(zzbc, (IInterface) zzei);
        zzbc.writeString(str);
        zzb(35, zzbc);
    }

    public final void zzb(zzek zzek, String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzb(47, zzbc);
    }

    public final void zzb(zzek zzek, String str, int i) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzbc.writeInt(i);
        zzb(33, zzbc);
    }

    public final void zzc(zzek zzek) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzb(15, zzbc);
    }

    public final void zzc(zzek zzek, String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzek);
        zzbc.writeString(str);
        zzb(32, zzbc);
    }
}
