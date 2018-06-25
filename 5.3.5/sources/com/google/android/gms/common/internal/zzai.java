package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;

final class zzai extends zzag implements Callback {
    private final Context mApplicationContext;
    private final Handler mHandler;
    private final HashMap<zzah, zzaj> zzggw = new HashMap();
    private final zza zzggx;
    private final long zzggy;
    private final long zzggz;

    zzai(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.zzggx = zza.zzanm();
        this.zzggy = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
        this.zzggz = 300000;
    }

    public final boolean handleMessage(Message message) {
        zzah zzah;
        zzaj zzaj;
        switch (message.what) {
            case 0:
                synchronized (this.zzggw) {
                    zzah = (zzah) message.obj;
                    zzaj = (zzaj) this.zzggw.get(zzah);
                    if (zzaj != null && zzaj.zzamv()) {
                        if (zzaj.isBound()) {
                            zzaj.zzgs("GmsClientSupervisor");
                        }
                        this.zzggw.remove(zzah);
                    }
                }
                return true;
            case 1:
                synchronized (this.zzggw) {
                    zzah = (zzah) message.obj;
                    zzaj = (zzaj) this.zzggw.get(zzah);
                    if (zzaj != null && zzaj.getState() == 3) {
                        String valueOf = String.valueOf(zzah);
                        Log.wtf("GmsClientSupervisor", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Timeout waiting for ServiceConnection callback ").append(valueOf).toString(), new Exception());
                        ComponentName componentName = zzaj.getComponentName();
                        if (componentName == null) {
                            componentName = zzah.getComponentName();
                        }
                        zzaj.onServiceDisconnected(componentName == null ? new ComponentName(zzah.getPackage(), "unknown") : componentName);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    protected final boolean zza(zzah zzah, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        zzbq.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzggw) {
            zzaj zzaj = (zzaj) this.zzggw.get(zzah);
            if (zzaj != null) {
                this.mHandler.removeMessages(0, zzah);
                if (!zzaj.zza(serviceConnection)) {
                    zzaj.zza(serviceConnection, str);
                    switch (zzaj.getState()) {
                        case 1:
                            serviceConnection.onServiceConnected(zzaj.getComponentName(), zzaj.getBinder());
                            break;
                        case 2:
                            zzaj.zzgr(str);
                            break;
                        default:
                            break;
                    }
                }
                String valueOf = String.valueOf(zzah);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append(valueOf).toString());
            }
            zzaj = new zzaj(this, zzah);
            zzaj.zza(serviceConnection, str);
            zzaj.zzgr(str);
            this.zzggw.put(zzah, zzaj);
            isBound = zzaj.isBound();
        }
        return isBound;
    }

    protected final void zzb(zzah zzah, ServiceConnection serviceConnection, String str) {
        zzbq.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zzggw) {
            zzaj zzaj = (zzaj) this.zzggw.get(zzah);
            String valueOf;
            if (zzaj == null) {
                valueOf = String.valueOf(zzah);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Nonexistent connection status for service config: ").append(valueOf).toString());
            } else if (zzaj.zza(serviceConnection)) {
                zzaj.zzb(serviceConnection, str);
                if (zzaj.zzamv()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, zzah), this.zzggy);
                }
            } else {
                valueOf = String.valueOf(zzah);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append(valueOf).toString());
            }
        }
    }
}
