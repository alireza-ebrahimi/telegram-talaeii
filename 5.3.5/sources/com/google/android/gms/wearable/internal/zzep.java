package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.PutDataRequest;

@Hide
public interface zzep extends IInterface {
    void zza(zzek zzek) throws RemoteException;

    void zza(zzek zzek, int i) throws RemoteException;

    void zza(zzek zzek, Uri uri) throws RemoteException;

    void zza(zzek zzek, Uri uri, int i) throws RemoteException;

    void zza(zzek zzek, Asset asset) throws RemoteException;

    void zza(zzek zzek, PutDataRequest putDataRequest) throws RemoteException;

    void zza(zzek zzek, zzd zzd) throws RemoteException;

    void zza(zzek zzek, zzei zzei, String str) throws RemoteException;

    void zza(zzek zzek, zzfw zzfw) throws RemoteException;

    void zza(zzek zzek, String str) throws RemoteException;

    void zza(zzek zzek, String str, int i) throws RemoteException;

    void zza(zzek zzek, String str, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void zza(zzek zzek, String str, ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException;

    void zza(zzek zzek, String str, String str2) throws RemoteException;

    void zza(zzek zzek, String str, String str2, byte[] bArr) throws RemoteException;

    void zzb(zzek zzek) throws RemoteException;

    void zzb(zzek zzek, Uri uri, int i) throws RemoteException;

    void zzb(zzek zzek, zzei zzei, String str) throws RemoteException;

    void zzb(zzek zzek, String str) throws RemoteException;

    void zzb(zzek zzek, String str, int i) throws RemoteException;

    void zzc(zzek zzek) throws RemoteException;

    void zzc(zzek zzek, String str) throws RemoteException;
}
