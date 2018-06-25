package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.api.internal.zzcz;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.zzal;

@Hide
public final class zzchh extends zzcfq {
    private final zzcha zziuk;

    public zzchh(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, str, zzr.zzcm(context));
    }

    public zzchh(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzr zzr) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, str, zzr);
        this.zziuk = new zzcha(context, this.zzitk);
    }

    public final void disconnect() {
        synchronized (this.zziuk) {
            if (isConnected()) {
                try {
                    this.zziuk.removeAllListeners();
                    this.zziuk.zzaxc();
                } catch (Throwable e) {
                    Log.e("LocationClientImpl", "Client disconnected before listeners could be cleaned up", e);
                }
            }
            super.disconnect();
        }
    }

    public final Location getLastLocation() throws RemoteException {
        return this.zziuk.getLastLocation();
    }

    public final void zza(long j, PendingIntent pendingIntent) throws RemoteException {
        zzalv();
        zzbq.checkNotNull(pendingIntent);
        zzbq.checkArgument(j >= 0, "detectionIntervalMillis must be >= 0");
        ((zzcgw) zzalw()).zza(j, true, pendingIntent);
    }

    public final void zza(PendingIntent pendingIntent, zzn<Status> zzn) throws RemoteException {
        zzalv();
        zzbq.checkNotNull(zzn, "ResultHolder not provided.");
        ((zzcgw) zzalw()).zza(pendingIntent, new zzcz(zzn));
    }

    public final void zza(PendingIntent pendingIntent, zzcgr zzcgr) throws RemoteException {
        this.zziuk.zza(pendingIntent, zzcgr);
    }

    public final void zza(zzck<LocationListener> zzck, zzcgr zzcgr) throws RemoteException {
        this.zziuk.zza((zzck) zzck, zzcgr);
    }

    public final void zza(zzcgr zzcgr) throws RemoteException {
        this.zziuk.zza(zzcgr);
    }

    public final void zza(zzchl zzchl, zzci<LocationCallback> zzci, zzcgr zzcgr) throws RemoteException {
        synchronized (this.zziuk) {
            this.zziuk.zza(zzchl, (zzci) zzci, zzcgr);
        }
    }

    public final void zza(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, zzn<Status> zzn) throws RemoteException {
        zzalv();
        zzbq.checkNotNull(zzn, "ResultHolder not provided.");
        ((zzcgw) zzalw()).zza(activityTransitionRequest, pendingIntent, new zzcz(zzn));
    }

    public final void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzn<Status> zzn) throws RemoteException {
        zzalv();
        zzbq.checkNotNull(geofencingRequest, "geofencingRequest can't be null.");
        zzbq.checkNotNull(pendingIntent, "PendingIntent must be specified.");
        zzbq.checkNotNull(zzn, "ResultHolder not provided.");
        ((zzcgw) zzalw()).zza(geofencingRequest, pendingIntent, new zzchi(zzn));
    }

    public final void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzcgr zzcgr) throws RemoteException {
        this.zziuk.zza(locationRequest, pendingIntent, zzcgr);
    }

    public final void zza(LocationRequest locationRequest, zzci<LocationListener> zzci, zzcgr zzcgr) throws RemoteException {
        synchronized (this.zziuk) {
            this.zziuk.zza(locationRequest, (zzci) zzci, zzcgr);
        }
    }

    public final void zza(LocationSettingsRequest locationSettingsRequest, zzn<LocationSettingsResult> zzn, String str) throws RemoteException {
        boolean z = true;
        zzalv();
        zzbq.checkArgument(locationSettingsRequest != null, "locationSettingsRequest can't be null nor empty.");
        if (zzn == null) {
            z = false;
        }
        zzbq.checkArgument(z, "listener can't be null.");
        ((zzcgw) zzalw()).zza(locationSettingsRequest, new zzchk(zzn), str);
    }

    public final void zza(zzal zzal, zzn<Status> zzn) throws RemoteException {
        zzalv();
        zzbq.checkNotNull(zzal, "removeGeofencingRequest can't be null.");
        zzbq.checkNotNull(zzn, "ResultHolder not provided.");
        ((zzcgw) zzalw()).zza(zzal, new zzchj(zzn));
    }

    public final LocationAvailability zzaxb() throws RemoteException {
        return this.zziuk.zzaxb();
    }

    public final void zzb(zzck<LocationCallback> zzck, zzcgr zzcgr) throws RemoteException {
        this.zziuk.zzb(zzck, zzcgr);
    }

    public final void zzbo(boolean z) throws RemoteException {
        this.zziuk.zzbo(z);
    }

    public final void zzc(PendingIntent pendingIntent) throws RemoteException {
        zzalv();
        zzbq.checkNotNull(pendingIntent);
        ((zzcgw) zzalw()).zzc(pendingIntent);
    }

    public final void zzc(Location location) throws RemoteException {
        this.zziuk.zzc(location);
    }
}
