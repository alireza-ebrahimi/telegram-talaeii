package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzca;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzal;

@Hide
public interface zzcgw extends IInterface {
    void zza(long j, boolean z, PendingIntent pendingIntent) throws RemoteException;

    void zza(PendingIntent pendingIntent, zzca zzca) throws RemoteException;

    void zza(zzcfw zzcfw) throws RemoteException;

    void zza(zzcgr zzcgr) throws RemoteException;

    void zza(zzchn zzchn) throws RemoteException;

    void zza(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, zzca zzca) throws RemoteException;

    void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzcgu zzcgu) throws RemoteException;

    void zza(LocationSettingsRequest locationSettingsRequest, zzcgy zzcgy, String str) throws RemoteException;

    void zza(zzal zzal, zzcgu zzcgu) throws RemoteException;

    void zzbo(boolean z) throws RemoteException;

    void zzc(PendingIntent pendingIntent) throws RemoteException;

    void zzc(Location location) throws RemoteException;

    Location zzim(String str) throws RemoteException;

    LocationAvailability zzin(String str) throws RemoteException;
}
