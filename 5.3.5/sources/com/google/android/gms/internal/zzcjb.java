package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import java.util.List;

@Hide
public interface zzcjb extends IInterface {
    List<zzcnl> zza(zzcif zzcif, boolean z) throws RemoteException;

    List<zzcii> zza(String str, String str2, zzcif zzcif) throws RemoteException;

    List<zzcnl> zza(String str, String str2, String str3, boolean z) throws RemoteException;

    List<zzcnl> zza(String str, String str2, boolean z, zzcif zzcif) throws RemoteException;

    void zza(long j, String str, String str2, String str3) throws RemoteException;

    void zza(zzcif zzcif) throws RemoteException;

    void zza(zzcii zzcii, zzcif zzcif) throws RemoteException;

    void zza(zzcix zzcix, zzcif zzcif) throws RemoteException;

    void zza(zzcix zzcix, String str, String str2) throws RemoteException;

    void zza(zzcnl zzcnl, zzcif zzcif) throws RemoteException;

    byte[] zza(zzcix zzcix, String str) throws RemoteException;

    void zzb(zzcif zzcif) throws RemoteException;

    void zzb(zzcii zzcii) throws RemoteException;

    String zzc(zzcif zzcif) throws RemoteException;

    void zzd(zzcif zzcif) throws RemoteException;

    List<zzcii> zzk(String str, String str2, String str3) throws RemoteException;
}
