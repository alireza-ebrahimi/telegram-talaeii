package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzck;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzu;
import com.google.android.gms.location.zzx;
import java.util.HashMap;
import java.util.Map;

@Hide
public final class zzcha {
    private final Context mContext;
    private final zzchr<zzcgw> zzitk;
    private ContentProviderClient zziuc = null;
    private boolean zziud = false;
    private final Map<zzck<LocationListener>, zzchf> zziue = new HashMap();
    private final Map<zzck<Object>, zzche> zziuf = new HashMap();
    private final Map<zzck<LocationCallback>, zzchb> zziug = new HashMap();

    public zzcha(Context context, zzchr<zzcgw> zzchr) {
        this.mContext = context;
        this.zzitk = zzchr;
    }

    private final zzchf zzm(zzci<LocationListener> zzci) {
        zzchf zzchf;
        synchronized (this.zziue) {
            zzchf = (zzchf) this.zziue.get(zzci.zzakx());
            if (zzchf == null) {
                zzchf = new zzchf(zzci);
            }
            this.zziue.put(zzci.zzakx(), zzchf);
        }
        return zzchf;
    }

    private final zzchb zzn(zzci<LocationCallback> zzci) {
        zzchb zzchb;
        synchronized (this.zziug) {
            zzchb = (zzchb) this.zziug.get(zzci.zzakx());
            if (zzchb == null) {
                zzchb = new zzchb(zzci);
            }
            this.zziug.put(zzci.zzakx(), zzchb);
        }
        return zzchb;
    }

    public final Location getLastLocation() throws RemoteException {
        this.zzitk.zzalv();
        return ((zzcgw) this.zzitk.zzalw()).zzim(this.mContext.getPackageName());
    }

    public final void removeAllListeners() throws RemoteException {
        synchronized (this.zziue) {
            for (zzx zzx : this.zziue.values()) {
                if (zzx != null) {
                    ((zzcgw) this.zzitk.zzalw()).zza(zzchn.zza(zzx, null));
                }
            }
            this.zziue.clear();
        }
        synchronized (this.zziug) {
            for (zzu zzu : this.zziug.values()) {
                if (zzu != null) {
                    ((zzcgw) this.zzitk.zzalw()).zza(zzchn.zza(zzu, null));
                }
            }
            this.zziug.clear();
        }
        synchronized (this.zziuf) {
            for (zzche zzche : this.zziuf.values()) {
                if (zzche != null) {
                    ((zzcgw) this.zzitk.zzalw()).zza(new zzcfw(2, null, zzche.asBinder(), null));
                }
            }
            this.zziuf.clear();
        }
    }

    public final void zza(PendingIntent pendingIntent, zzcgr zzcgr) throws RemoteException {
        this.zzitk.zzalv();
        ((zzcgw) this.zzitk.zzalw()).zza(new zzchn(2, null, null, pendingIntent, null, zzcgr != null ? zzcgr.asBinder() : null));
    }

    public final void zza(zzck<LocationListener> zzck, zzcgr zzcgr) throws RemoteException {
        this.zzitk.zzalv();
        zzbq.checkNotNull(zzck, "Invalid null listener key");
        synchronized (this.zziue) {
            zzx zzx = (zzchf) this.zziue.remove(zzck);
            if (zzx != null) {
                zzx.release();
                ((zzcgw) this.zzitk.zzalw()).zza(zzchn.zza(zzx, zzcgr));
            }
        }
    }

    public final void zza(zzcgr zzcgr) throws RemoteException {
        this.zzitk.zzalv();
        ((zzcgw) this.zzitk.zzalw()).zza(zzcgr);
    }

    public final void zza(zzchl zzchl, zzci<LocationCallback> zzci, zzcgr zzcgr) throws RemoteException {
        this.zzitk.zzalv();
        ((zzcgw) this.zzitk.zzalw()).zza(new zzchn(1, zzchl, null, null, zzn(zzci).asBinder(), zzcgr != null ? zzcgr.asBinder() : null));
    }

    public final void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzcgr zzcgr) throws RemoteException {
        this.zzitk.zzalv();
        ((zzcgw) this.zzitk.zzalw()).zza(new zzchn(1, zzchl.zza(locationRequest), null, pendingIntent, null, zzcgr != null ? zzcgr.asBinder() : null));
    }

    public final void zza(LocationRequest locationRequest, zzci<LocationListener> zzci, zzcgr zzcgr) throws RemoteException {
        this.zzitk.zzalv();
        ((zzcgw) this.zzitk.zzalw()).zza(new zzchn(1, zzchl.zza(locationRequest), zzm(zzci).asBinder(), null, null, zzcgr != null ? zzcgr.asBinder() : null));
    }

    public final LocationAvailability zzaxb() throws RemoteException {
        this.zzitk.zzalv();
        return ((zzcgw) this.zzitk.zzalw()).zzin(this.mContext.getPackageName());
    }

    public final void zzaxc() throws RemoteException {
        if (this.zziud) {
            zzbo(false);
        }
    }

    public final void zzb(zzck<LocationCallback> zzck, zzcgr zzcgr) throws RemoteException {
        this.zzitk.zzalv();
        zzbq.checkNotNull(zzck, "Invalid null listener key");
        synchronized (this.zziug) {
            zzu zzu = (zzchb) this.zziug.remove(zzck);
            if (zzu != null) {
                zzu.release();
                ((zzcgw) this.zzitk.zzalw()).zza(zzchn.zza(zzu, zzcgr));
            }
        }
    }

    public final void zzbo(boolean z) throws RemoteException {
        this.zzitk.zzalv();
        ((zzcgw) this.zzitk.zzalw()).zzbo(z);
        this.zziud = z;
    }

    public final void zzc(Location location) throws RemoteException {
        this.zzitk.zzalv();
        ((zzcgw) this.zzitk.zzalw()).zzc(location);
    }
}
