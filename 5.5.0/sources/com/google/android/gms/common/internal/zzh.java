package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.HashMap;
import javax.annotation.concurrent.GuardedBy;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;

final class zzh extends GmsClientSupervisor implements Callback {
    private final Handler mHandler;
    private final Context zzau;
    @GuardedBy("mConnectionStatus")
    private final HashMap<ConnectionStatusConfig, zzi> zztr = new HashMap();
    private final ConnectionTracker zzts;
    private final long zztt;
    private final long zztu;

    zzh(Context context) {
        this.zzau = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.zzts = ConnectionTracker.getInstance();
        this.zztt = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
        this.zztu = 300000;
    }

    protected final boolean bindService(ConnectionStatusConfig connectionStatusConfig, ServiceConnection serviceConnection, String str) {
        boolean isBound;
        Preconditions.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zztr) {
            zzi zzi = (zzi) this.zztr.get(connectionStatusConfig);
            if (zzi != null) {
                this.mHandler.removeMessages(0, connectionStatusConfig);
                if (!zzi.zza(serviceConnection)) {
                    zzi.zza(serviceConnection, str);
                    switch (zzi.getState()) {
                        case 1:
                            serviceConnection.onServiceConnected(zzi.getComponentName(), zzi.getBinder());
                            break;
                        case 2:
                            zzi.zzj(str);
                            break;
                        default:
                            break;
                    }
                }
                String valueOf = String.valueOf(connectionStatusConfig);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 81).append("Trying to bind a GmsServiceConnection that was already connected before.  config=").append(valueOf).toString());
            }
            zzi = new zzi(this, connectionStatusConfig);
            zzi.zza(serviceConnection, str);
            zzi.zzj(str);
            this.zztr.put(connectionStatusConfig, zzi);
            isBound = zzi.isBound();
        }
        return isBound;
    }

    public final boolean handleMessage(Message message) {
        ConnectionStatusConfig connectionStatusConfig;
        zzi zzi;
        switch (message.what) {
            case 0:
                synchronized (this.zztr) {
                    connectionStatusConfig = (ConnectionStatusConfig) message.obj;
                    zzi = (zzi) this.zztr.get(connectionStatusConfig);
                    if (zzi != null && zzi.zzcv()) {
                        if (zzi.isBound()) {
                            zzi.zzk("GmsClientSupervisor");
                        }
                        this.zztr.remove(connectionStatusConfig);
                    }
                }
                return true;
            case 1:
                synchronized (this.zztr) {
                    connectionStatusConfig = (ConnectionStatusConfig) message.obj;
                    zzi = (zzi) this.zztr.get(connectionStatusConfig);
                    if (zzi != null && zzi.getState() == 3) {
                        String valueOf = String.valueOf(connectionStatusConfig);
                        Log.wtf("GmsClientSupervisor", new StringBuilder(String.valueOf(valueOf).length() + 47).append("Timeout waiting for ServiceConnection callback ").append(valueOf).toString(), new Exception());
                        ComponentName componentName = zzi.getComponentName();
                        if (componentName == null) {
                            componentName = connectionStatusConfig.getComponentName();
                        }
                        zzi.onServiceDisconnected(componentName == null ? new ComponentName(connectionStatusConfig.getPackage(), "unknown") : componentName);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    @VisibleForTesting
    public final void resetForTesting() {
        synchronized (this.zztr) {
            for (zzi zzi : this.zztr.values()) {
                this.mHandler.removeMessages(0, zzi.zztx);
                if (zzi.isBound()) {
                    zzi.zzk("GmsClientSupervisor");
                }
            }
            this.zztr.clear();
        }
    }

    protected final void unbindService(ConnectionStatusConfig connectionStatusConfig, ServiceConnection serviceConnection, String str) {
        Preconditions.checkNotNull(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zztr) {
            zzi zzi = (zzi) this.zztr.get(connectionStatusConfig);
            String valueOf;
            if (zzi == null) {
                valueOf = String.valueOf(connectionStatusConfig);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 50).append("Nonexistent connection status for service config: ").append(valueOf).toString());
            } else if (zzi.zza(serviceConnection)) {
                zzi.zzb(serviceConnection, str);
                if (zzi.zzcv()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, connectionStatusConfig), this.zztt);
                }
            } else {
                valueOf = String.valueOf(connectionStatusConfig);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 76).append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=").append(valueOf).toString());
            }
        }
    }
}
