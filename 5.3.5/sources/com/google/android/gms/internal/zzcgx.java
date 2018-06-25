package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzca;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzal;

public final class zzcgx extends zzev implements zzcgw {
    zzcgx(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
    }

    public final void zza(long j, boolean z, PendingIntent pendingIntent) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeLong(j);
        zzex.zza(zzbc, true);
        zzex.zza(zzbc, (Parcelable) pendingIntent);
        zzb(5, zzbc);
    }

    public final void zza(PendingIntent pendingIntent, zzca zzca) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) pendingIntent);
        zzex.zza(zzbc, (IInterface) zzca);
        zzb(73, zzbc);
    }

    public final void zza(zzcfw zzcfw) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzcfw);
        zzb(75, zzbc);
    }

    public final void zza(zzcgr zzcgr) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (IInterface) zzcgr);
        zzb(67, zzbc);
    }

    public final void zza(zzchn zzchn) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzchn);
        zzb(59, zzbc);
    }

    public final void zza(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, zzca zzca) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) activityTransitionRequest);
        zzex.zza(zzbc, (Parcelable) pendingIntent);
        zzex.zza(zzbc, (IInterface) zzca);
        zzb(72, zzbc);
    }

    public final void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzcgu zzcgu) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) geofencingRequest);
        zzex.zza(zzbc, (Parcelable) pendingIntent);
        zzex.zza(zzbc, (IInterface) zzcgu);
        zzb(57, zzbc);
    }

    public final void zza(LocationSettingsRequest locationSettingsRequest, zzcgy zzcgy, String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) locationSettingsRequest);
        zzex.zza(zzbc, (IInterface) zzcgy);
        zzbc.writeString(str);
        zzb(63, zzbc);
    }

    public final void zza(zzal zzal, zzcgu zzcgu) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) zzal);
        zzex.zza(zzbc, (IInterface) zzcgu);
        zzb(74, zzbc);
    }

    public final void zzbo(boolean z) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, z);
        zzb(12, zzbc);
    }

    public final void zzc(PendingIntent pendingIntent) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) pendingIntent);
        zzb(6, zzbc);
    }

    public final void zzc(Location location) throws RemoteException {
        Parcel zzbc = zzbc();
        zzex.zza(zzbc, (Parcelable) location);
        zzb(13, zzbc);
    }

    public final Location zzim(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        Parcel zza = zza(21, zzbc);
        Location location = (Location) zzex.zza(zza, Location.CREATOR);
        zza.recycle();
        return location;
    }

    public final LocationAvailability zzin(String str) throws RemoteException {
        Parcel zzbc = zzbc();
        zzbc.writeString(str);
        Parcel zza = zza(34, zzbc);
        LocationAvailability locationAvailability = (LocationAvailability) zzex.zza(zza, LocationAvailability.CREATOR);
        zza.recycle();
        return locationAvailability;
    }
}
