package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.HashSet;
import java.util.Set;

final class zzaj implements ServiceConnection {
    private ComponentName mComponentName;
    private int mState = 2;
    private IBinder zzgfp;
    private final Set<ServiceConnection> zzgha = new HashSet();
    private boolean zzghb;
    private final zzah zzghc;
    private /* synthetic */ zzai zzghd;

    public zzaj(zzai zzai, zzah zzah) {
        this.zzghd = zzai;
        this.zzghc = zzah;
    }

    public final IBinder getBinder() {
        return this.zzgfp;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isBound() {
        return this.zzghb;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zzghd.zzggw) {
            this.zzghd.mHandler.removeMessages(1, this.zzghc);
            this.zzgfp = iBinder;
            this.mComponentName = componentName;
            for (ServiceConnection onServiceConnected : this.zzgha) {
                onServiceConnected.onServiceConnected(componentName, iBinder);
            }
            this.mState = 1;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zzghd.zzggw) {
            this.zzghd.mHandler.removeMessages(1, this.zzghc);
            this.zzgfp = null;
            this.mComponentName = componentName;
            for (ServiceConnection onServiceDisconnected : this.zzgha) {
                onServiceDisconnected.onServiceDisconnected(componentName);
            }
            this.mState = 2;
        }
    }

    public final void zza(ServiceConnection serviceConnection, String str) {
        this.zzghd.zzggx;
        this.zzghd.mApplicationContext;
        this.zzghc.zzcq(this.zzghd.mApplicationContext);
        this.zzgha.add(serviceConnection);
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zzgha.contains(serviceConnection);
    }

    public final boolean zzamv() {
        return this.zzgha.isEmpty();
    }

    public final void zzb(ServiceConnection serviceConnection, String str) {
        this.zzghd.zzggx;
        this.zzghd.mApplicationContext;
        this.zzgha.remove(serviceConnection);
    }

    public final void zzgr(String str) {
        this.mState = 3;
        this.zzghb = this.zzghd.zzggx.zza(this.zzghd.mApplicationContext, str, this.zzghc.zzcq(this.zzghd.mApplicationContext), this, this.zzghc.zzamu());
        if (this.zzghb) {
            this.zzghd.mHandler.sendMessageDelayed(this.zzghd.mHandler.obtainMessage(1, this.zzghc), this.zzghd.zzggz);
            return;
        }
        this.mState = 2;
        try {
            this.zzghd.zzggx;
            this.zzghd.mApplicationContext.unbindService(this);
        } catch (IllegalArgumentException e) {
        }
    }

    public final void zzgs(String str) {
        this.zzghd.mHandler.removeMessages(1, this.zzghc);
        this.zzghd.zzggx;
        this.zzghd.mApplicationContext.unbindService(this);
        this.zzghb = false;
        this.mState = 2;
    }
}
