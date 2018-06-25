package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.stats.zza;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

public final class zzcms implements ServiceConnection, zzf, zzg {
    final /* synthetic */ zzcme zzjri;
    private volatile boolean zzjrp;
    private volatile zzcji zzjrq;

    protected zzcms(zzcme zzcme) {
        this.zzjri = zzcme;
    }

    @MainThread
    public final void onConnected(@Nullable Bundle bundle) {
        zzbq.zzgn("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                zzcjb zzcjb = (zzcjb) this.zzjrq.zzalw();
                this.zzjrq = null;
                this.zzjri.zzayo().zzh(new zzcmv(this, zzcjb));
            } catch (DeadObjectException e) {
                this.zzjrq = null;
                this.zzjrp = false;
            } catch (IllegalStateException e2) {
                this.zzjrq = null;
                this.zzjrp = false;
            }
        }
    }

    @MainThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzbq.zzgn("MeasurementServiceConnection.onConnectionFailed");
        zzcjj zzbbo = this.zzjri.zzjev.zzbbo();
        if (zzbbo != null) {
            zzbbo.zzbaw().zzj("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzjrp = false;
            this.zzjrq = null;
        }
        this.zzjri.zzayo().zzh(new zzcmx(this));
    }

    @MainThread
    public final void onConnectionSuspended(int i) {
        zzbq.zzgn("MeasurementServiceConnection.onConnectionSuspended");
        this.zzjri.zzayp().zzbaz().log("Service connection suspended");
        this.zzjri.zzayo().zzh(new zzcmw(this));
    }

    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzcjb zzcjb;
        zzbq.zzgn("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder == null) {
                this.zzjrp = false;
                this.zzjri.zzayp().zzbau().log("Service connected with null binder");
                return;
            }
            try {
                String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                    if (iBinder == null) {
                        zzcjb = null;
                    } else {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                        zzcjb = queryLocalInterface instanceof zzcjb ? (zzcjb) queryLocalInterface : new zzcjd(iBinder);
                    }
                    try {
                        this.zzjri.zzayp().zzbba().log("Bound to IMeasurementService interface");
                    } catch (RemoteException e) {
                        this.zzjri.zzayp().zzbau().log("Service connect failed to get IMeasurementService");
                        if (zzcjb != null) {
                            this.zzjrp = false;
                            try {
                                zza.zzanm();
                                this.zzjri.getContext().unbindService(this.zzjri.zzjrb);
                            } catch (IllegalArgumentException e2) {
                            }
                        } else {
                            this.zzjri.zzayo().zzh(new zzcmt(this, zzcjb));
                        }
                    }
                    if (zzcjb != null) {
                        this.zzjrp = false;
                        zza.zzanm();
                        this.zzjri.getContext().unbindService(this.zzjri.zzjrb);
                    } else {
                        this.zzjri.zzayo().zzh(new zzcmt(this, zzcjb));
                    }
                }
                this.zzjri.zzayp().zzbau().zzj("Got binder with a wrong descriptor", interfaceDescriptor);
                zzcjb = null;
                if (zzcjb != null) {
                    this.zzjri.zzayo().zzh(new zzcmt(this, zzcjb));
                } else {
                    this.zzjrp = false;
                    zza.zzanm();
                    this.zzjri.getContext().unbindService(this.zzjri.zzjrb);
                }
            } catch (RemoteException e3) {
                zzcjb = null;
                this.zzjri.zzayp().zzbau().log("Service connect failed to get IMeasurementService");
                if (zzcjb != null) {
                    this.zzjri.zzayo().zzh(new zzcmt(this, zzcjb));
                } else {
                    this.zzjrp = false;
                    zza.zzanm();
                    this.zzjri.getContext().unbindService(this.zzjri.zzjrb);
                }
            }
        }
    }

    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) {
        zzbq.zzgn("MeasurementServiceConnection.onServiceDisconnected");
        this.zzjri.zzayp().zzbaz().log("Service disconnected");
        this.zzjri.zzayo().zzh(new zzcmu(this, componentName));
    }

    @WorkerThread
    public final void zzbcm() {
        this.zzjri.zzwj();
        Context context = this.zzjri.getContext();
        synchronized (this) {
            if (this.zzjrp) {
                this.zzjri.zzayp().zzbba().log("Connection attempt already in progress");
            } else if (this.zzjrq != null) {
                this.zzjri.zzayp().zzbba().log("Already awaiting connection attempt");
            } else {
                this.zzjrq = new zzcji(context, Looper.getMainLooper(), this, this);
                this.zzjri.zzayp().zzbba().log("Connecting to remote service");
                this.zzjrp = true;
                this.zzjrq.zzals();
            }
        }
    }

    @WorkerThread
    public final void zzm(Intent intent) {
        this.zzjri.zzwj();
        Context context = this.zzjri.getContext();
        zza zzanm = zza.zzanm();
        synchronized (this) {
            if (this.zzjrp) {
                this.zzjri.zzayp().zzbba().log("Connection attempt already in progress");
                return;
            }
            this.zzjri.zzayp().zzbba().log("Using local app measurement service");
            this.zzjrp = true;
            zzanm.zza(context, intent, this.zzjri.zzjrb, TsExtractor.TS_STREAM_TYPE_AC3);
        }
    }
}
