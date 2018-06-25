package com.google.android.gms.gcm;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.util.zzz;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    private ComponentName componentName;
    private final Object lock = new Object();
    private ExecutorService zzair;
    private int zzijt;
    private Messenger zziju;
    private GcmNetworkManager zzijv;

    @TargetApi(21)
    class zza extends Handler {
        private /* synthetic */ GcmTaskService zzijx;

        zza(GcmTaskService gcmTaskService, Looper looper) {
            this.zzijx = gcmTaskService;
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (zzz.zzb(this.zzijx, message.sendingUid, "com.google.android.gms")) {
                String valueOf;
                switch (message.what) {
                    case 1:
                        Bundle data = message.getData();
                        if (!data.isEmpty()) {
                            Messenger messenger = message.replyTo;
                            if (messenger != null) {
                                String string = data.getString("tag");
                                List parcelableArrayList = data.getParcelableArrayList("triggered_uris");
                                if (!this.zzijx.zzig(string)) {
                                    this.zzijx.zza(new zzb(this.zzijx, string, messenger, data.getBundle("extras"), parcelableArrayList));
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    case 2:
                        if (Log.isLoggable("GcmTaskService", 3)) {
                            valueOf = String.valueOf(message);
                            Log.d("GcmTaskService", new StringBuilder(String.valueOf(valueOf).length() + 45).append("ignoring unimplemented stop message for now: ").append(valueOf).toString());
                            return;
                        }
                        return;
                    case 4:
                        this.zzijx.onInitializeTasks();
                        return;
                    default:
                        valueOf = String.valueOf(message);
                        Log.e("GcmTaskService", new StringBuilder(String.valueOf(valueOf).length() + 31).append("Unrecognized message received: ").append(valueOf).toString());
                        return;
                }
            }
            Log.e("GcmTaskService", "unable to verify presence of Google Play Services");
        }
    }

    class zzb implements Runnable {
        private final Bundle mExtras;
        @Nullable
        private final Messenger mMessenger;
        private final String mTag;
        private /* synthetic */ GcmTaskService zzijx;
        private final List<Uri> zzijy;
        @Nullable
        private final zzd zzijz;

        zzb(GcmTaskService gcmTaskService, @NonNull String str, IBinder iBinder, Bundle bundle, List<Uri> list) {
            zzd zzd;
            this.zzijx = gcmTaskService;
            this.mTag = str;
            if (iBinder == null) {
                zzd = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.gcm.INetworkTaskCallback");
                zzd = queryLocalInterface instanceof zzd ? (zzd) queryLocalInterface : new zze(iBinder);
            }
            this.zzijz = zzd;
            this.mExtras = bundle;
            this.zzijy = list;
            this.mMessenger = null;
        }

        zzb(GcmTaskService gcmTaskService, @NonNull String str, Messenger messenger, Bundle bundle, List<Uri> list) {
            this.zzijx = gcmTaskService;
            this.mTag = str;
            this.mMessenger = messenger;
            this.mExtras = bundle;
            this.zzijy = list;
            this.zzijz = null;
        }

        private final boolean zzawg() {
            return this.mMessenger != null;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final void zzea(int r7) {
            /*
            r6 = this;
            r0 = r6.zzijx;
            r1 = r0.lock;
            monitor-enter(r1);
            r0 = r6.zzijx;	 Catch:{ RemoteException -> 0x00d4 }
            r0 = r0.zzijv;	 Catch:{ RemoteException -> 0x00d4 }
            r2 = r6.mTag;	 Catch:{ RemoteException -> 0x00d4 }
            r3 = r6.zzijx;	 Catch:{ RemoteException -> 0x00d4 }
            r3 = r3.componentName;	 Catch:{ RemoteException -> 0x00d4 }
            r3 = r3.getClassName();	 Catch:{ RemoteException -> 0x00d4 }
            r0 = r0.zzac(r2, r3);	 Catch:{ RemoteException -> 0x00d4 }
            if (r0 == 0) goto L_0x005d;
        L_0x001f:
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzijv;	 Catch:{ all -> 0x00cb }
            r2 = r6.mTag;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r3 = r3.componentName;	 Catch:{ all -> 0x00cb }
            r3 = r3.getClassName();	 Catch:{ all -> 0x00cb }
            r0.zzab(r2, r3);	 Catch:{ all -> 0x00cb }
            r0 = r6.zzawg();	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x005b;
        L_0x003a:
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzijv;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r2.componentName;	 Catch:{ all -> 0x00cb }
            r2 = r2.getClassName();	 Catch:{ all -> 0x00cb }
            r0 = r0.zzif(r2);	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x005b;
        L_0x0050:
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzijt;	 Catch:{ all -> 0x00cb }
            r0.stopSelf(r2);	 Catch:{ all -> 0x00cb }
        L_0x005b:
            monitor-exit(r1);	 Catch:{ all -> 0x00cb }
        L_0x005c:
            return;
        L_0x005d:
            r0 = r6.zzawg();	 Catch:{ RemoteException -> 0x00d4 }
            if (r0 == 0) goto L_0x00ce;
        L_0x0063:
            r0 = r6.mMessenger;	 Catch:{ RemoteException -> 0x00d4 }
            r2 = android.os.Message.obtain();	 Catch:{ RemoteException -> 0x00d4 }
            r3 = 3;
            r2.what = r3;	 Catch:{ RemoteException -> 0x00d4 }
            r2.arg1 = r7;	 Catch:{ RemoteException -> 0x00d4 }
            r3 = new android.os.Bundle;	 Catch:{ RemoteException -> 0x00d4 }
            r3.<init>();	 Catch:{ RemoteException -> 0x00d4 }
            r4 = "component";
            r5 = r6.zzijx;	 Catch:{ RemoteException -> 0x00d4 }
            r5 = r5.componentName;	 Catch:{ RemoteException -> 0x00d4 }
            r3.putParcelable(r4, r5);	 Catch:{ RemoteException -> 0x00d4 }
            r4 = "tag";
            r5 = r6.mTag;	 Catch:{ RemoteException -> 0x00d4 }
            r3.putString(r4, r5);	 Catch:{ RemoteException -> 0x00d4 }
            r2.setData(r3);	 Catch:{ RemoteException -> 0x00d4 }
            r0.send(r2);	 Catch:{ RemoteException -> 0x00d4 }
        L_0x008d:
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzijv;	 Catch:{ all -> 0x00cb }
            r2 = r6.mTag;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r3 = r3.componentName;	 Catch:{ all -> 0x00cb }
            r3 = r3.getClassName();	 Catch:{ all -> 0x00cb }
            r0.zzab(r2, r3);	 Catch:{ all -> 0x00cb }
            r0 = r6.zzawg();	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9;
        L_0x00a8:
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzijv;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r2.componentName;	 Catch:{ all -> 0x00cb }
            r2 = r2.getClassName();	 Catch:{ all -> 0x00cb }
            r0 = r0.zzif(r2);	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9;
        L_0x00be:
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzijt;	 Catch:{ all -> 0x00cb }
            r0.stopSelf(r2);	 Catch:{ all -> 0x00cb }
        L_0x00c9:
            monitor-exit(r1);	 Catch:{ all -> 0x00cb }
            goto L_0x005c;
        L_0x00cb:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x00cb }
            throw r0;
        L_0x00ce:
            r0 = r6.zzijz;	 Catch:{ RemoteException -> 0x00d4 }
            r0.zzeb(r7);	 Catch:{ RemoteException -> 0x00d4 }
            goto L_0x008d;
        L_0x00d4:
            r0 = move-exception;
            r2 = "GcmTaskService";
            r3 = "Error reporting result of operation to scheduler for ";
            r0 = r6.mTag;	 Catch:{ all -> 0x0131 }
            r0 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0131 }
            r4 = r0.length();	 Catch:{ all -> 0x0131 }
            if (r4 == 0) goto L_0x012b;
        L_0x00e7:
            r0 = r3.concat(r0);	 Catch:{ all -> 0x0131 }
        L_0x00eb:
            android.util.Log.e(r2, r0);	 Catch:{ all -> 0x0131 }
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzijv;	 Catch:{ all -> 0x00cb }
            r2 = r6.mTag;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r3 = r3.componentName;	 Catch:{ all -> 0x00cb }
            r3 = r3.getClassName();	 Catch:{ all -> 0x00cb }
            r0.zzab(r2, r3);	 Catch:{ all -> 0x00cb }
            r0 = r6.zzawg();	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9;
        L_0x0109:
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzijv;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r2.componentName;	 Catch:{ all -> 0x00cb }
            r2 = r2.getClassName();	 Catch:{ all -> 0x00cb }
            r0 = r0.zzif(r2);	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9;
        L_0x011f:
            r0 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzijt;	 Catch:{ all -> 0x00cb }
            r0.stopSelf(r2);	 Catch:{ all -> 0x00cb }
            goto L_0x00c9;
        L_0x012b:
            r0 = new java.lang.String;	 Catch:{ all -> 0x0131 }
            r0.<init>(r3);	 Catch:{ all -> 0x0131 }
            goto L_0x00eb;
        L_0x0131:
            r0 = move-exception;
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzijv;	 Catch:{ all -> 0x00cb }
            r3 = r6.mTag;	 Catch:{ all -> 0x00cb }
            r4 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r4 = r4.componentName;	 Catch:{ all -> 0x00cb }
            r4 = r4.getClassName();	 Catch:{ all -> 0x00cb }
            r2.zzab(r3, r4);	 Catch:{ all -> 0x00cb }
            r2 = r6.zzawg();	 Catch:{ all -> 0x00cb }
            if (r2 != 0) goto L_0x016e;
        L_0x014d:
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzijv;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r3 = r3.componentName;	 Catch:{ all -> 0x00cb }
            r3 = r3.getClassName();	 Catch:{ all -> 0x00cb }
            r2 = r2.zzif(r3);	 Catch:{ all -> 0x00cb }
            if (r2 != 0) goto L_0x016e;
        L_0x0163:
            r2 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzijx;	 Catch:{ all -> 0x00cb }
            r3 = r3.zzijt;	 Catch:{ all -> 0x00cb }
            r2.stopSelf(r3);	 Catch:{ all -> 0x00cb }
        L_0x016e:
            throw r0;	 Catch:{ all -> 0x00cb }
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.GcmTaskService.zzb.zzea(int):void");
        }

        public final void run() {
            zzea(this.zzijx.onRunTask(new TaskParams(this.mTag, this.mExtras, this.zzijy)));
        }
    }

    private final void zza(zzb zzb) {
        try {
            this.zzair.execute(zzb);
        } catch (Throwable e) {
            Log.e("GcmTaskService", "Executor is shutdown. onDestroy was called but main looper had an unprocessed start task message. The task will be retried with backoff delay.", e);
            zzb.zzea(1);
        }
    }

    private final void zzdz(int i) {
        synchronized (this.lock) {
            this.zzijt = i;
            if (!this.zzijv.zzif(this.componentName.getClassName())) {
                stopSelf(this.zzijt);
            }
        }
    }

    @Hide
    private final boolean zzig(String str) {
        boolean z;
        synchronized (this.lock) {
            z = !this.zzijv.zzaa(str, this.componentName.getClassName());
            if (z) {
                String packageName = getPackageName();
                Log.w("GcmTaskService", new StringBuilder((String.valueOf(packageName).length() + 44) + String.valueOf(str).length()).append(packageName).append(" ").append(str).append(": Task already running, won't start another").toString());
            }
        }
        return z;
    }

    @CallSuper
    public IBinder onBind(Intent intent) {
        return (intent != null && zzs.zzanx() && SERVICE_ACTION_EXECUTE_TASK.equals(intent.getAction())) ? this.zziju.getBinder() : null;
    }

    @CallSuper
    public void onCreate() {
        super.onCreate();
        this.zzijv = GcmNetworkManager.getInstance(this);
        this.zzair = Executors.newFixedThreadPool(2, new zzb(this));
        this.zziju = new Messenger(new zza(this, Looper.getMainLooper()));
        this.componentName = new ComponentName(this, getClass());
    }

    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        List shutdownNow = this.zzair.shutdownNow();
        if (!shutdownNow.isEmpty()) {
            Log.e("GcmTaskService", "Shutting down, but not all tasks are finished executing. Remaining: " + shutdownNow.size());
        }
    }

    public void onInitializeTasks() {
    }

    public abstract int onRunTask(TaskParams taskParams);

    @CallSuper
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            zzdz(i2);
        } else {
            try {
                intent.setExtrasClassLoader(PendingCallback.class.getClassLoader());
                String action = intent.getAction();
                if (SERVICE_ACTION_EXECUTE_TASK.equals(action)) {
                    String stringExtra = intent.getStringExtra("tag");
                    Parcelable parcelableExtra = intent.getParcelableExtra("callback");
                    Bundle bundleExtra = intent.getBundleExtra("extras");
                    List parcelableArrayListExtra = intent.getParcelableArrayListExtra("triggered_uris");
                    if (!(parcelableExtra instanceof PendingCallback)) {
                        String packageName = getPackageName();
                        Log.e("GcmTaskService", new StringBuilder((String.valueOf(packageName).length() + 47) + String.valueOf(stringExtra).length()).append(packageName).append(" ").append(stringExtra).append(": Could not process request, invalid callback.").toString());
                    } else if (zzig(stringExtra)) {
                        zzdz(i2);
                    } else {
                        zza(new zzb(this, stringExtra, ((PendingCallback) parcelableExtra).zzgfp, bundleExtra, parcelableArrayListExtra));
                    }
                } else if (SERVICE_ACTION_INITIALIZE.equals(action)) {
                    onInitializeTasks();
                } else {
                    Log.e("GcmTaskService", new StringBuilder(String.valueOf(action).length() + 37).append("Unknown action received ").append(action).append(", terminating").toString());
                }
                zzdz(i2);
            } finally {
                zzdz(i2);
            }
        }
        return 2;
    }
}
