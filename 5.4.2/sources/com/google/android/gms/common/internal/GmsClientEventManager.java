package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class GmsClientEventManager implements Callback {
    private final Handler mHandler;
    private final Object mLock = new Object();
    private final GmsClientEventState zztf;
    private final ArrayList<ConnectionCallbacks> zztg = new ArrayList();
    @VisibleForTesting
    private final ArrayList<ConnectionCallbacks> zzth = new ArrayList();
    private final ArrayList<OnConnectionFailedListener> zzti = new ArrayList();
    private volatile boolean zztj = false;
    private final AtomicInteger zztk = new AtomicInteger(0);
    private boolean zztl = false;

    @VisibleForTesting
    public interface GmsClientEventState {
        Bundle getConnectionHint();

        boolean isConnected();
    }

    public GmsClientEventManager(Looper looper, GmsClientEventState gmsClientEventState) {
        this.zztf = gmsClientEventState;
        this.mHandler = new Handler(looper, this);
    }

    public final boolean areCallbacksEnabled() {
        return this.zztj;
    }

    public final void disableCallbacks() {
        this.zztj = false;
        this.zztk.incrementAndGet();
    }

    public final void enableCallbacks() {
        this.zztj = true;
    }

    public final boolean handleMessage(Message message) {
        if (message.what == 1) {
            ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) message.obj;
            synchronized (this.mLock) {
                if (this.zztj && this.zztf.isConnected() && this.zztg.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.zztf.getConnectionHint());
                }
            }
            return true;
        }
        Log.wtf("GmsClientEvents", "Don't know how to handle message: " + message.what, new Exception());
        return false;
    }

    public final boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks) {
        boolean contains;
        Preconditions.checkNotNull(connectionCallbacks);
        synchronized (this.mLock) {
            contains = this.zztg.contains(connectionCallbacks);
        }
        return contains;
    }

    public final boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener) {
        boolean contains;
        Preconditions.checkNotNull(onConnectionFailedListener);
        synchronized (this.mLock) {
            contains = this.zzti.contains(onConnectionFailedListener);
        }
        return contains;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @com.google.android.gms.common.util.VisibleForTesting
    public final void onConnectionFailure(com.google.android.gms.common.ConnectionResult r8) {
        /*
        r7 = this;
        r1 = 1;
        r2 = 0;
        r0 = android.os.Looper.myLooper();
        r3 = r7.mHandler;
        r3 = r3.getLooper();
        if (r0 != r3) goto L_0x0048;
    L_0x000e:
        r0 = r1;
    L_0x000f:
        r3 = "onConnectionFailure must only be called on the Handler thread";
        com.google.android.gms.common.internal.Preconditions.checkState(r0, r3);
        r0 = r7.mHandler;
        r0.removeMessages(r1);
        r3 = r7.mLock;
        monitor-enter(r3);
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0056 }
        r1 = r7.zzti;	 Catch:{ all -> 0x0056 }
        r0.<init>(r1);	 Catch:{ all -> 0x0056 }
        r1 = r7.zztk;	 Catch:{ all -> 0x0056 }
        r4 = r1.get();	 Catch:{ all -> 0x0056 }
        r0 = (java.util.ArrayList) r0;	 Catch:{ all -> 0x0056 }
        r5 = r0.size();	 Catch:{ all -> 0x0056 }
    L_0x0030:
        if (r2 >= r5) goto L_0x0059;
    L_0x0032:
        r1 = r0.get(r2);	 Catch:{ all -> 0x0056 }
        r2 = r2 + 1;
        r1 = (com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener) r1;	 Catch:{ all -> 0x0056 }
        r6 = r7.zztj;	 Catch:{ all -> 0x0056 }
        if (r6 == 0) goto L_0x0046;
    L_0x003e:
        r6 = r7.zztk;	 Catch:{ all -> 0x0056 }
        r6 = r6.get();	 Catch:{ all -> 0x0056 }
        if (r6 == r4) goto L_0x004a;
    L_0x0046:
        monitor-exit(r3);	 Catch:{ all -> 0x0056 }
    L_0x0047:
        return;
    L_0x0048:
        r0 = r2;
        goto L_0x000f;
    L_0x004a:
        r6 = r7.zzti;	 Catch:{ all -> 0x0056 }
        r6 = r6.contains(r1);	 Catch:{ all -> 0x0056 }
        if (r6 == 0) goto L_0x0030;
    L_0x0052:
        r1.onConnectionFailed(r8);	 Catch:{ all -> 0x0056 }
        goto L_0x0030;
    L_0x0056:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0056 }
        throw r0;
    L_0x0059:
        monitor-exit(r3);	 Catch:{ all -> 0x0056 }
        goto L_0x0047;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.GmsClientEventManager.onConnectionFailure(com.google.android.gms.common.ConnectionResult):void");
    }

    @VisibleForTesting
    protected final void onConnectionSuccess() {
        synchronized (this.mLock) {
            onConnectionSuccess(this.zztf.getConnectionHint());
        }
    }

    @VisibleForTesting
    public final void onConnectionSuccess(Bundle bundle) {
        boolean z = true;
        int i = 0;
        Preconditions.checkState(Looper.myLooper() == this.mHandler.getLooper(), "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.mLock) {
            Preconditions.checkState(!this.zztl);
            this.mHandler.removeMessages(1);
            this.zztl = true;
            if (this.zzth.size() != 0) {
                z = false;
            }
            Preconditions.checkState(z);
            ArrayList arrayList = new ArrayList(this.zztg);
            int i2 = this.zztk.get();
            arrayList = arrayList;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) obj;
                if (this.zztj && this.zztf.isConnected() && this.zztk.get() == i2) {
                    if (!this.zzth.contains(connectionCallbacks)) {
                        connectionCallbacks.onConnected(bundle);
                    }
                }
            }
            this.zzth.clear();
            this.zztl = false;
        }
    }

    @VisibleForTesting
    public final void onUnintentionalDisconnection(int i) {
        int i2 = 0;
        Preconditions.checkState(Looper.myLooper() == this.mHandler.getLooper(), "onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.mLock) {
            this.zztl = true;
            ArrayList arrayList = new ArrayList(this.zztg);
            int i3 = this.zztk.get();
            arrayList = arrayList;
            int size = arrayList.size();
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) obj;
                if (this.zztj && this.zztk.get() == i3) {
                    if (this.zztg.contains(connectionCallbacks)) {
                        connectionCallbacks.onConnectionSuspended(i);
                    }
                }
            }
            this.zzth.clear();
            this.zztl = false;
        }
    }

    public final void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        Preconditions.checkNotNull(connectionCallbacks);
        synchronized (this.mLock) {
            if (this.zztg.contains(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(valueOf).length() + 62).append("registerConnectionCallbacks(): listener ").append(valueOf).append(" is already registered").toString());
            } else {
                this.zztg.add(connectionCallbacks);
            }
        }
        if (this.zztf.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, connectionCallbacks));
        }
    }

    public final void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        Preconditions.checkNotNull(onConnectionFailedListener);
        synchronized (this.mLock) {
            if (this.zzti.contains(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(valueOf).length() + 67).append("registerConnectionFailedListener(): listener ").append(valueOf).append(" is already registered").toString());
            } else {
                this.zzti.add(onConnectionFailedListener);
            }
        }
    }

    public final void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        Preconditions.checkNotNull(connectionCallbacks);
        synchronized (this.mLock) {
            if (!this.zztg.remove(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(valueOf).length() + 52).append("unregisterConnectionCallbacks(): listener ").append(valueOf).append(" not found").toString());
            } else if (this.zztl) {
                this.zzth.add(connectionCallbacks);
            }
        }
    }

    public final void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        Preconditions.checkNotNull(onConnectionFailedListener);
        synchronized (this.mLock) {
            if (!this.zzti.remove(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                Log.w("GmsClientEvents", new StringBuilder(String.valueOf(valueOf).length() + 57).append("unregisterConnectionFailedListener(): listener ").append(valueOf).append(" not found").toString());
            }
        }
    }
}
