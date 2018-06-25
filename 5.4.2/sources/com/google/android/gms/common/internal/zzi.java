package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.HashSet;
import java.util.Set;

final class zzi implements ServiceConnection {
    private ComponentName mComponentName;
    private int mState = 2;
    private IBinder zzry;
    private final Set<ServiceConnection> zztv = new HashSet();
    private boolean zztw;
    private final ConnectionStatusConfig zztx;
    private final /* synthetic */ zzh zzty;

    public zzi(zzh zzh, ConnectionStatusConfig connectionStatusConfig) {
        this.zzty = zzh;
        this.zztx = connectionStatusConfig;
    }

    public final IBinder getBinder() {
        return this.zzry;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isBound() {
        return this.zztw;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zzty.zztr) {
            this.zzty.mHandler.removeMessages(1, this.zztx);
            this.zzry = iBinder;
            this.mComponentName = componentName;
            for (ServiceConnection onServiceConnected : this.zztv) {
                onServiceConnected.onServiceConnected(componentName, iBinder);
            }
            this.mState = 1;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zzty.zztr) {
            this.zzty.mHandler.removeMessages(1, this.zztx);
            this.zzry = null;
            this.mComponentName = componentName;
            for (ServiceConnection onServiceDisconnected : this.zztv) {
                onServiceDisconnected.onServiceDisconnected(componentName);
            }
            this.mState = 2;
        }
    }

    public final void zza(ServiceConnection serviceConnection, String str) {
        this.zzty.zzts.logConnectService(this.zzty.zzau, serviceConnection, str, this.zztx.getStartServiceIntent(this.zzty.zzau));
        this.zztv.add(serviceConnection);
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zztv.contains(serviceConnection);
    }

    public final void zzb(ServiceConnection serviceConnection, String str) {
        this.zzty.zzts.logDisconnectService(this.zzty.zzau, serviceConnection);
        this.zztv.remove(serviceConnection);
    }

    public final boolean zzcv() {
        return this.zztv.isEmpty();
    }

    public final void zzj(String str) {
        this.mState = 3;
        this.zztw = this.zzty.zzts.bindService(this.zzty.zzau, str, this.zztx.getStartServiceIntent(this.zzty.zzau), this, this.zztx.getBindFlags());
        if (this.zztw) {
            this.zzty.mHandler.sendMessageDelayed(this.zzty.mHandler.obtainMessage(1, this.zztx), this.zzty.zztu);
            return;
        }
        this.mState = 2;
        try {
            this.zzty.zzts.unbindService(this.zzty.zzau, this);
        } catch (IllegalArgumentException e) {
        }
    }

    public final void zzk(String str) {
        this.zzty.mHandler.removeMessages(1, this.zztx);
        this.zzty.zzts.unbindService(this.zzty.zzau, this);
        this.zztw = false;
        this.mState = 2;
    }
}
