package com.google.android.gms.wearable;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.ChannelClient.Channel;
import com.google.android.gms.wearable.ChannelClient.ChannelCallback;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.internal.zzah;
import com.google.android.gms.wearable.internal.zzas;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzen;
import com.google.android.gms.wearable.internal.zzfe;
import com.google.android.gms.wearable.internal.zzfo;
import com.google.android.gms.wearable.internal.zzhp;
import com.google.android.gms.wearable.internal.zzi;
import com.google.android.gms.wearable.internal.zzl;
import java.util.List;

public class WearableListenerService extends Service implements CapabilityListener, ChannelListener, DataListener, MessageListener {
    public static final String BIND_LISTENER_INTENT_ACTION = "com.google.android.gms.wearable.BIND_LISTENER";
    private ComponentName service;
    private zzc zzad;
    private IBinder zzae;
    private Intent zzaf;
    private Looper zzag;
    private final Object zzah = new Object();
    private boolean zzai;
    private zzas zzaj = new zzas(new zza());

    class zza extends ChannelCallback {
        private final /* synthetic */ WearableListenerService zzak;

        private zza(WearableListenerService wearableListenerService) {
            this.zzak = wearableListenerService;
        }

        public final void onChannelClosed(Channel channel, int i, int i2) {
            this.zzak.onChannelClosed(channel, i, i2);
        }

        public final void onChannelOpened(Channel channel) {
            this.zzak.onChannelOpened(channel);
        }

        public final void onInputClosed(Channel channel, int i, int i2) {
            this.zzak.onInputClosed(channel, i, i2);
        }

        public final void onOutputClosed(Channel channel, int i, int i2) {
            this.zzak.onOutputClosed(channel, i, i2);
        }
    }

    class zzb implements ServiceConnection {
        private zzb(WearableListenerService wearableListenerService) {
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    final class zzc extends Handler {
        private boolean started;
        private final /* synthetic */ WearableListenerService zzak;
        private final zzb zzal = new zzb();

        zzc(WearableListenerService wearableListenerService, Looper looper) {
            this.zzak = wearableListenerService;
            super(looper);
        }

        @SuppressLint({"UntrackedBindService"})
        private final synchronized void zzb() {
            if (!this.started) {
                if (Log.isLoggable("WearableLS", 2)) {
                    String valueOf = String.valueOf(this.zzak.service);
                    Log.v("WearableLS", new StringBuilder(String.valueOf(valueOf).length() + 13).append("bindService: ").append(valueOf).toString());
                }
                this.zzak.bindService(this.zzak.zzaf, this.zzal, 1);
                this.started = true;
            }
        }

        @SuppressLint({"UntrackedBindService"})
        private final synchronized void zzb(String str) {
            if (this.started) {
                if (Log.isLoggable("WearableLS", 2)) {
                    String valueOf = String.valueOf(this.zzak.service);
                    Log.v("WearableLS", new StringBuilder((String.valueOf(str).length() + 17) + String.valueOf(valueOf).length()).append("unbindService: ").append(str).append(", ").append(valueOf).toString());
                }
                try {
                    this.zzak.unbindService(this.zzal);
                } catch (Throwable e) {
                    Log.e("WearableLS", "Exception when unbinding from local service", e);
                }
                this.started = false;
            }
        }

        public final void dispatchMessage(Message message) {
            zzb();
            try {
                super.dispatchMessage(message);
            } finally {
                if (!hasMessages(0)) {
                    zzb("dispatch");
                }
            }
        }

        final void quit() {
            getLooper().quit();
            zzb("quit");
        }
    }

    final class zzd extends zzen {
        final /* synthetic */ WearableListenerService zzak;
        private volatile int zzam;

        private zzd(WearableListenerService wearableListenerService) {
            this.zzak = wearableListenerService;
            this.zzam = -1;
        }

        private final boolean zza(Runnable runnable, String str, Object obj) {
            if (Log.isLoggable("WearableLS", 3)) {
                Log.d("WearableLS", String.format("%s: %s %s", new Object[]{str, this.zzak.service.toString(), obj}));
            }
            int callingUid = Binder.getCallingUid();
            if (callingUid == this.zzam) {
                callingUid = 1;
            } else if (zzhp.zza(this.zzak).zze("com.google.android.wearable.app.cn") && UidVerifier.uidHasPackageName(this.zzak, callingUid, "com.google.android.wearable.app.cn")) {
                this.zzam = callingUid;
                callingUid = 1;
            } else if (UidVerifier.isGooglePlayServicesUid(this.zzak, callingUid)) {
                this.zzam = callingUid;
                callingUid = 1;
            } else {
                Log.e("WearableLS", "Caller is not GooglePlayServices; caller UID: " + callingUid);
                boolean z = false;
            }
            if (callingUid == 0) {
                return false;
            }
            synchronized (this.zzak.zzah) {
                if (this.zzak.zzai) {
                    return false;
                }
                this.zzak.zzad.post(runnable);
                return true;
            }
        }

        public final void onConnectedNodes(List<zzfo> list) {
            zza(new zzp(this, list), "onConnectedNodes", list);
        }

        public final void zza(DataHolder dataHolder) {
            Runnable zzl = new zzl(this, dataHolder);
            try {
                String valueOf = String.valueOf(dataHolder);
                if (!zza(zzl, "onDataItemChanged", new StringBuilder(String.valueOf(valueOf).length() + 18).append(valueOf).append(", rows=").append(dataHolder.getCount()).toString())) {
                }
            } finally {
                dataHolder.close();
            }
        }

        public final void zza(zzah zzah) {
            zza(new zzq(this, zzah), "onConnectedCapabilityChanged", zzah);
        }

        public final void zza(zzaw zzaw) {
            zza(new zzt(this, zzaw), "onChannelEvent", zzaw);
        }

        public final void zza(zzfe zzfe) {
            zza(new zzm(this, zzfe), "onMessageReceived", zzfe);
        }

        public final void zza(zzfo zzfo) {
            zza(new zzn(this, zzfo), "onPeerConnected", zzfo);
        }

        public final void zza(zzi zzi) {
            zza(new zzs(this, zzi), "onEntityUpdate", zzi);
        }

        public final void zza(zzl zzl) {
            zza(new zzr(this, zzl), "onNotificationReceived", zzl);
        }

        public final void zzb(zzfo zzfo) {
            zza(new zzo(this, zzfo), "onPeerDisconnected", zzfo);
        }
    }

    public Looper getLooper() {
        if (this.zzag == null) {
            HandlerThread handlerThread = new HandlerThread("WearableListenerService");
            handlerThread.start();
            this.zzag = handlerThread.getLooper();
        }
        return this.zzag;
    }

    public final IBinder onBind(Intent intent) {
        return BIND_LISTENER_INTENT_ACTION.equals(intent.getAction()) ? this.zzae : null;
    }

    public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
    }

    public void onChannelClosed(Channel channel, int i, int i2) {
    }

    public void onChannelClosed(Channel channel, int i, int i2) {
    }

    public void onChannelOpened(Channel channel) {
    }

    public void onChannelOpened(Channel channel) {
    }

    public void onConnectedNodes(List<Node> list) {
    }

    public void onCreate() {
        super.onCreate();
        this.service = new ComponentName(this, getClass().getName());
        if (Log.isLoggable("WearableLS", 3)) {
            String valueOf = String.valueOf(this.service);
            Log.d("WearableLS", new StringBuilder(String.valueOf(valueOf).length() + 10).append("onCreate: ").append(valueOf).toString());
        }
        this.zzad = new zzc(this, getLooper());
        this.zzaf = new Intent(BIND_LISTENER_INTENT_ACTION);
        this.zzaf.setComponent(this.service);
        this.zzae = new zzd();
    }

    public void onDataChanged(DataEventBuffer dataEventBuffer) {
    }

    public void onDestroy() {
        if (Log.isLoggable("WearableLS", 3)) {
            String valueOf = String.valueOf(this.service);
            Log.d("WearableLS", new StringBuilder(String.valueOf(valueOf).length() + 11).append("onDestroy: ").append(valueOf).toString());
        }
        synchronized (this.zzah) {
            this.zzai = true;
            if (this.zzad == null) {
                String valueOf2 = String.valueOf(this.service);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf2).length() + 111).append("onDestroy: mServiceHandler not set, did you override onCreate() but forget to call super.onCreate()? component=").append(valueOf2).toString());
            } else {
                this.zzad.quit();
            }
        }
        super.onDestroy();
    }

    public void onEntityUpdate(zzb zzb) {
    }

    public void onInputClosed(Channel channel, int i, int i2) {
    }

    public void onInputClosed(Channel channel, int i, int i2) {
    }

    public void onMessageReceived(MessageEvent messageEvent) {
    }

    public void onNotificationReceived(zzd zzd) {
    }

    public void onOutputClosed(Channel channel, int i, int i2) {
    }

    public void onOutputClosed(Channel channel, int i, int i2) {
    }

    public void onPeerConnected(Node node) {
    }

    public void onPeerDisconnected(Node node) {
    }
}
