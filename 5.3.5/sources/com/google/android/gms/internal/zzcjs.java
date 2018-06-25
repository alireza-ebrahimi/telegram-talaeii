package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

class zzcjs extends BroadcastReceiver {
    private static String zzedv = zzcjs.class.getName();
    private final zzckj zzjev;
    private boolean zzjlg;
    private boolean zzjlh;

    zzcjs(zzckj zzckj) {
        zzbq.checkNotNull(zzckj);
        this.zzjev = zzckj;
    }

    @MainThread
    public void onReceive(Context context, Intent intent) {
        this.zzjev.zzyk();
        String action = intent.getAction();
        this.zzjev.zzayp().zzbba().zzj("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zzaax = this.zzjev.zzbbs().zzaax();
            if (this.zzjlh != zzaax) {
                this.zzjlh = zzaax;
                this.zzjev.zzayo().zzh(new zzcjt(this, zzaax));
                return;
            }
            return;
        }
        this.zzjev.zzayp().zzbaw().zzj("NetworkBroadcastReceiver received unknown action", action);
    }

    @WorkerThread
    public final void unregister() {
        this.zzjev.zzyk();
        this.zzjev.zzayo().zzwj();
        this.zzjev.zzayo().zzwj();
        if (this.zzjlg) {
            this.zzjev.zzayp().zzbba().log("Unregistering connectivity change receiver");
            this.zzjlg = false;
            this.zzjlh = false;
            try {
                this.zzjev.getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzjev.zzayp().zzbau().zzj("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    @WorkerThread
    public final void zzaau() {
        this.zzjev.zzyk();
        this.zzjev.zzayo().zzwj();
        if (!this.zzjlg) {
            this.zzjev.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.zzjlh = this.zzjev.zzbbs().zzaax();
            this.zzjev.zzayp().zzbba().zzj("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzjlh));
            this.zzjlg = true;
        }
    }
}
