package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;

@Hide
final class zzh extends Handler {
    private /* synthetic */ zzd zzgfk;

    public zzh(zzd zzd, Looper looper) {
        this.zzgfk = zzd;
        super(looper);
    }

    private static void zza(Message message) {
        zzi zzi = (zzi) message.obj;
        zzi.zzamb();
        zzi.unregister();
    }

    private static boolean zzb(Message message) {
        return message.what == 2 || message.what == 1 || message.what == 7;
    }

    public final void handleMessage(Message message) {
        PendingIntent pendingIntent = null;
        if (this.zzgfk.zzgfh.get() != message.arg1) {
            if (zzb(message)) {
                zza(message);
            }
        } else if ((message.what == 1 || message.what == 7 || message.what == 4 || message.what == 5) && !this.zzgfk.isConnecting()) {
            zza(message);
        } else if (message.what == 4) {
            this.zzgfk.zzgff = new ConnectionResult(message.arg2);
            if (!this.zzgfk.zzalz() || this.zzgfk.zzgfg) {
                r0 = this.zzgfk.zzgff != null ? this.zzgfk.zzgff : new ConnectionResult(8);
                this.zzgfk.zzgew.zzf(r0);
                this.zzgfk.onConnectionFailed(r0);
                return;
            }
            this.zzgfk.zza(3, null);
        } else if (message.what == 5) {
            r0 = this.zzgfk.zzgff != null ? this.zzgfk.zzgff : new ConnectionResult(8);
            this.zzgfk.zzgew.zzf(r0);
            this.zzgfk.onConnectionFailed(r0);
        } else if (message.what == 3) {
            if (message.obj instanceof PendingIntent) {
                pendingIntent = (PendingIntent) message.obj;
            }
            ConnectionResult connectionResult = new ConnectionResult(message.arg2, pendingIntent);
            this.zzgfk.zzgew.zzf(connectionResult);
            this.zzgfk.onConnectionFailed(connectionResult);
        } else if (message.what == 6) {
            this.zzgfk.zza(5, null);
            if (this.zzgfk.zzgfb != null) {
                this.zzgfk.zzgfb.onConnectionSuspended(message.arg2);
            }
            this.zzgfk.onConnectionSuspended(message.arg2);
            this.zzgfk.zza(5, 1, null);
        } else if (message.what == 2 && !this.zzgfk.isConnected()) {
            zza(message);
        } else if (zzb(message)) {
            ((zzi) message.obj).zzamc();
        } else {
            Log.wtf("GmsClient", "Don't know how to handle message: " + message.what, new Exception());
        }
    }
}
