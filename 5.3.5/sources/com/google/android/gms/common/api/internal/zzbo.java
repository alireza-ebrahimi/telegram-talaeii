package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzbz;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzcyj;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class zzbo<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener, zzu {
    private final zzh<O> zzfsn;
    private final zze zzfwd;
    private boolean zzfye;
    final /* synthetic */ zzbm zzfzq;
    private final Queue<zza> zzfzr = new LinkedList();
    private final zzb zzfzs;
    private final zzae zzfzt;
    private final Set<zzj> zzfzu = new HashSet();
    private final Map<zzck<?>, zzcr> zzfzv = new HashMap();
    private final int zzfzw;
    private final zzcv zzfzx;
    private int zzfzy = -1;
    private ConnectionResult zzfzz = null;

    @WorkerThread
    public zzbo(zzbm zzbm, GoogleApi<O> googleApi) {
        this.zzfzq = zzbm;
        this.zzfwd = googleApi.zza(zzbm.mHandler.getLooper(), this);
        if (this.zzfwd instanceof zzbz) {
            this.zzfzs = zzbz.zzanb();
        } else {
            this.zzfzs = this.zzfwd;
        }
        this.zzfsn = googleApi.zzahv();
        this.zzfzt = new zzae();
        this.zzfzw = googleApi.getInstanceId();
        if (this.zzfwd.zzacc()) {
            this.zzfzx = googleApi.zza(zzbm.mContext, zzbm.mHandler);
        } else {
            this.zzfzx = null;
        }
    }

    private final void zzake() {
        this.zzfzy = -1;
        this.zzfzq.zzfzk = -1;
    }

    @WorkerThread
    private final void zzakf() {
        zzaki();
        zzi(ConnectionResult.zzfqt);
        zzakk();
        for (zzcr zzcr : this.zzfzv.values()) {
            try {
                zzcr.zzfty.zzb(this.zzfzs, new TaskCompletionSource());
            } catch (DeadObjectException e) {
                onConnectionSuspended(1);
                this.zzfwd.disconnect();
            } catch (RemoteException e2) {
            }
        }
        while (this.zzfwd.isConnected() && !this.zzfzr.isEmpty()) {
            zzb((zza) this.zzfzr.remove());
        }
        zzakl();
    }

    @WorkerThread
    private final void zzakg() {
        zzaki();
        this.zzfye = true;
        this.zzfzt.zzaje();
        this.zzfzq.mHandler.sendMessageDelayed(Message.obtain(this.zzfzq.mHandler, 9, this.zzfsn), this.zzfzq.zzfyg);
        this.zzfzq.mHandler.sendMessageDelayed(Message.obtain(this.zzfzq.mHandler, 11, this.zzfsn), this.zzfzq.zzfyf);
        zzake();
    }

    @WorkerThread
    private final void zzakk() {
        if (this.zzfye) {
            this.zzfzq.mHandler.removeMessages(11, this.zzfsn);
            this.zzfzq.mHandler.removeMessages(9, this.zzfsn);
            this.zzfye = false;
        }
    }

    private final void zzakl() {
        this.zzfzq.mHandler.removeMessages(12, this.zzfsn);
        this.zzfzq.mHandler.sendMessageDelayed(this.zzfzq.mHandler.obtainMessage(12, this.zzfsn), this.zzfzq.zzfzi);
    }

    @WorkerThread
    private final void zzb(zza zza) {
        zza.zza(this.zzfzt, zzacc());
        try {
            zza.zza(this);
        } catch (DeadObjectException e) {
            onConnectionSuspended(1);
            this.zzfwd.disconnect();
        }
    }

    @WorkerThread
    private final void zzi(ConnectionResult connectionResult) {
        for (zzj zzj : this.zzfzu) {
            String str = null;
            if (connectionResult == ConnectionResult.zzfqt) {
                str = this.zzfwd.zzahp();
            }
            zzj.zza(this.zzfsn, connectionResult, str);
        }
        this.zzfzu.clear();
    }

    @WorkerThread
    public final void connect() {
        zzbq.zza(this.zzfzq.mHandler);
        if (!this.zzfwd.isConnected() && !this.zzfwd.isConnecting()) {
            if (this.zzfwd.zzahn()) {
                this.zzfwd.zzahq();
                if (this.zzfzq.zzfzk != 0) {
                    this.zzfzq.zzftg;
                    int zzc = zzf.zzc(this.zzfzq.mContext, this.zzfwd.zzahq());
                    this.zzfwd.zzahq();
                    this.zzfzq.zzfzk = zzc;
                    if (zzc != 0) {
                        onConnectionFailed(new ConnectionResult(zzc, null));
                        return;
                    }
                }
            }
            zzj zzbu = new zzbu(this.zzfzq, this.zzfwd, this.zzfsn);
            if (this.zzfwd.zzacc()) {
                this.zzfzx.zza((zzcy) zzbu);
            }
            this.zzfwd.zza(zzbu);
        }
    }

    public final int getInstanceId() {
        return this.zzfzw;
    }

    final boolean isConnected() {
        return this.zzfwd.isConnected();
    }

    public final void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == this.zzfzq.mHandler.getLooper()) {
            zzakf();
        } else {
            this.zzfzq.mHandler.post(new zzbp(this));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.WorkerThread
    public final void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult r6) {
        /*
        r5 = this;
        r0 = r5.zzfzq;
        r0 = r0.mHandler;
        com.google.android.gms.common.internal.zzbq.zza(r0);
        r0 = r5.zzfzx;
        if (r0 == 0) goto L_0x0012;
    L_0x000d:
        r0 = r5.zzfzx;
        r0.zzakz();
    L_0x0012:
        r5.zzaki();
        r5.zzake();
        r5.zzi(r6);
        r0 = r6.getErrorCode();
        r1 = 4;
        if (r0 != r1) goto L_0x002a;
    L_0x0022:
        r0 = com.google.android.gms.common.api.internal.zzbm.zzfzh;
        r5.zzw(r0);
    L_0x0029:
        return;
    L_0x002a:
        r0 = r5.zzfzr;
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0035;
    L_0x0032:
        r5.zzfzz = r6;
        goto L_0x0029;
    L_0x0035:
        r1 = com.google.android.gms.common.api.internal.zzbm.sLock;
        monitor-enter(r1);
        r0 = r5.zzfzq;	 Catch:{ all -> 0x005d }
        r0 = r0.zzfzn;	 Catch:{ all -> 0x005d }
        if (r0 == 0) goto L_0x0060;
    L_0x0042:
        r0 = r5.zzfzq;	 Catch:{ all -> 0x005d }
        r0 = r0.zzfzo;	 Catch:{ all -> 0x005d }
        r2 = r5.zzfsn;	 Catch:{ all -> 0x005d }
        r0 = r0.contains(r2);	 Catch:{ all -> 0x005d }
        if (r0 == 0) goto L_0x0060;
    L_0x0050:
        r0 = r5.zzfzq;	 Catch:{ all -> 0x005d }
        r0 = r0.zzfzn;	 Catch:{ all -> 0x005d }
        r2 = r5.zzfzw;	 Catch:{ all -> 0x005d }
        r0.zzb(r6, r2);	 Catch:{ all -> 0x005d }
        monitor-exit(r1);	 Catch:{ all -> 0x005d }
        goto L_0x0029;
    L_0x005d:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x005d }
        throw r0;
    L_0x0060:
        monitor-exit(r1);	 Catch:{ all -> 0x005d }
        r0 = r5.zzfzq;
        r1 = r5.zzfzw;
        r0 = r0.zzc(r6, r1);
        if (r0 != 0) goto L_0x0029;
    L_0x006b:
        r0 = r6.getErrorCode();
        r1 = 18;
        if (r0 != r1) goto L_0x0076;
    L_0x0073:
        r0 = 1;
        r5.zzfye = r0;
    L_0x0076:
        r0 = r5.zzfye;
        if (r0 == 0) goto L_0x0098;
    L_0x007a:
        r0 = r5.zzfzq;
        r0 = r0.mHandler;
        r1 = r5.zzfzq;
        r1 = r1.mHandler;
        r2 = 9;
        r3 = r5.zzfsn;
        r1 = android.os.Message.obtain(r1, r2, r3);
        r2 = r5.zzfzq;
        r2 = r2.zzfyg;
        r0.sendMessageDelayed(r1, r2);
        goto L_0x0029;
    L_0x0098:
        r0 = new com.google.android.gms.common.api.Status;
        r1 = 17;
        r2 = r5.zzfsn;
        r2 = r2.zzaig();
        r3 = java.lang.String.valueOf(r2);
        r3 = r3.length();
        r3 = r3 + 38;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "API: ";
        r3 = r4.append(r3);
        r2 = r3.append(r2);
        r3 = " is not available on this device.";
        r2 = r2.append(r3);
        r2 = r2.toString();
        r0.<init>(r1, r2);
        r5.zzw(r0);
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzbo.onConnectionFailed(com.google.android.gms.common.ConnectionResult):void");
    }

    public final void onConnectionSuspended(int i) {
        if (Looper.myLooper() == this.zzfzq.mHandler.getLooper()) {
            zzakg();
        } else {
            this.zzfzq.mHandler.post(new zzbq(this));
        }
    }

    @WorkerThread
    public final void resume() {
        zzbq.zza(this.zzfzq.mHandler);
        if (this.zzfye) {
            connect();
        }
    }

    @WorkerThread
    public final void signOut() {
        zzbq.zza(this.zzfzq.mHandler);
        zzw(zzbm.zzfzg);
        this.zzfzt.zzajd();
        for (zzck zzf : (zzck[]) this.zzfzv.keySet().toArray(new zzck[this.zzfzv.size()])) {
            zza(new zzf(zzf, new TaskCompletionSource()));
        }
        zzi(new ConnectionResult(4));
        if (this.zzfwd.isConnected()) {
            this.zzfwd.zza(new zzbs(this));
        }
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (Looper.myLooper() == this.zzfzq.mHandler.getLooper()) {
            onConnectionFailed(connectionResult);
        } else {
            this.zzfzq.mHandler.post(new zzbr(this, connectionResult));
        }
    }

    @WorkerThread
    public final void zza(zza zza) {
        zzbq.zza(this.zzfzq.mHandler);
        if (this.zzfwd.isConnected()) {
            zzb(zza);
            zzakl();
            return;
        }
        this.zzfzr.add(zza);
        if (this.zzfzz == null || !this.zzfzz.hasResolution()) {
            connect();
        } else {
            onConnectionFailed(this.zzfzz);
        }
    }

    @WorkerThread
    public final void zza(zzj zzj) {
        zzbq.zza(this.zzfzq.mHandler);
        this.zzfzu.add(zzj);
    }

    public final boolean zzacc() {
        return this.zzfwd.zzacc();
    }

    public final zze zzaix() {
        return this.zzfwd;
    }

    @WorkerThread
    public final void zzajr() {
        zzbq.zza(this.zzfzq.mHandler);
        if (this.zzfye) {
            zzakk();
            zzw(this.zzfzq.zzftg.isGooglePlayServicesAvailable(this.zzfzq.mContext) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
            this.zzfwd.disconnect();
        }
    }

    public final Map<zzck<?>, zzcr> zzakh() {
        return this.zzfzv;
    }

    @WorkerThread
    public final void zzaki() {
        zzbq.zza(this.zzfzq.mHandler);
        this.zzfzz = null;
    }

    @WorkerThread
    public final ConnectionResult zzakj() {
        zzbq.zza(this.zzfzq.mHandler);
        return this.zzfzz;
    }

    @WorkerThread
    public final void zzakm() {
        zzbq.zza(this.zzfzq.mHandler);
        if (!this.zzfwd.isConnected() || this.zzfzv.size() != 0) {
            return;
        }
        if (this.zzfzt.zzajc()) {
            zzakl();
        } else {
            this.zzfwd.disconnect();
        }
    }

    final zzcyj zzakn() {
        return this.zzfzx == null ? null : this.zzfzx.zzakn();
    }

    @WorkerThread
    public final void zzh(@NonNull ConnectionResult connectionResult) {
        zzbq.zza(this.zzfzq.mHandler);
        this.zzfwd.disconnect();
        onConnectionFailed(connectionResult);
    }

    @WorkerThread
    public final void zzw(Status status) {
        zzbq.zza(this.zzfzq.mHandler);
        for (zza zzs : this.zzfzr) {
            zzs.zzs(status);
        }
        this.zzfzr.clear();
    }
}
