package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

final class zzga implements ServiceConnection {
    final /* synthetic */ zzfy zzalh;

    private zzga(zzfy zzfy) {
        this.zzalh = zzfy;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            this.zzalh.zzacw.zzgf().zziv().log("Install Referrer connection returned with null binder");
            return;
        }
        try {
            this.zzalh.zzalf = zzs.zza(iBinder);
            if (this.zzalh.zzalf == null) {
                this.zzalh.zzacw.zzgf().zziv().log("Install Referrer Service implementation was not found");
                return;
            }
            this.zzalh.zzacw.zzgf().zzix().log("Install Referrer Service connected");
            this.zzalh.zzacw.zzge().zzc(new zzgb(this));
        } catch (Exception e) {
            this.zzalh.zzacw.zzgf().zziv().zzg("Exception occurred while calling Install Referrer API", e);
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        this.zzalh.zzalf = null;
        this.zzalh.zzacw.zzgf().zzix().log("Install Referrer Service disconnected");
    }
}
