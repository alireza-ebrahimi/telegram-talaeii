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
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.concurrent.GuardedBy;

public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    private ComponentName componentName;
    private final Object lock = new Object();
    @GuardedBy("lock")
    private int zzt;
    private ExecutorService zzu;
    private Messenger zzv;
    private GcmNetworkManager zzw;

    @TargetApi(21)
    @VisibleForTesting
    class zzd extends Handler {
        private final /* synthetic */ GcmTaskService zzy;

        zzd(GcmTaskService gcmTaskService, Looper looper) {
            this.zzy = gcmTaskService;
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (UidVerifier.uidHasPackageName(this.zzy, message.sendingUid, "com.google.android.gms")) {
                String valueOf;
                switch (message.what) {
                    case 1:
                        Bundle data = message.getData();
                        if (!data.isEmpty()) {
                            Messenger messenger = message.replyTo;
                            if (messenger != null) {
                                String string = data.getString("tag");
                                List parcelableArrayList = data.getParcelableArrayList("triggered_uris");
                                if (!this.zzy.zzg(string)) {
                                    this.zzy.zzd(new zze(this.zzy, string, messenger, data.getBundle("extras"), parcelableArrayList));
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
                        this.zzy.onInitializeTasks();
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

    class zze implements Runnable {
        private final Bundle extras;
        private final String tag;
        private final zzg zzaa;
        private final Messenger zzab;
        private final /* synthetic */ GcmTaskService zzy;
        private final List<Uri> zzz;

        zze(GcmTaskService gcmTaskService, String str, IBinder iBinder, Bundle bundle, List<Uri> list) {
            zzg zzg;
            this.zzy = gcmTaskService;
            this.tag = str;
            if (iBinder == null) {
                zzg = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.gcm.INetworkTaskCallback");
                zzg = queryLocalInterface instanceof zzg ? (zzg) queryLocalInterface : new zzh(iBinder);
            }
            this.zzaa = zzg;
            this.extras = bundle;
            this.zzz = list;
            this.zzab = null;
        }

        zze(GcmTaskService gcmTaskService, String str, Messenger messenger, Bundle bundle, List<Uri> list) {
            this.zzy = gcmTaskService;
            this.tag = str;
            this.zzab = messenger;
            this.extras = bundle;
            this.zzz = list;
            this.zzaa = null;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final void zze(int r7) {
            /*
            r6 = this;
            r0 = r6.zzy;
            r1 = r0.lock;
            monitor-enter(r1);
            r0 = r6.zzy;	 Catch:{ RemoteException -> 0x00d4 }
            r0 = r0.zzw;	 Catch:{ RemoteException -> 0x00d4 }
            r2 = r6.tag;	 Catch:{ RemoteException -> 0x00d4 }
            r3 = r6.zzy;	 Catch:{ RemoteException -> 0x00d4 }
            r3 = r3.componentName;	 Catch:{ RemoteException -> 0x00d4 }
            r3 = r3.getClassName();	 Catch:{ RemoteException -> 0x00d4 }
            r0 = r0.zzf(r2, r3);	 Catch:{ RemoteException -> 0x00d4 }
            if (r0 == 0) goto L_0x005d;
        L_0x001f:
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzw;	 Catch:{ all -> 0x00cb }
            r2 = r6.tag;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r3 = r3.componentName;	 Catch:{ all -> 0x00cb }
            r3 = r3.getClassName();	 Catch:{ all -> 0x00cb }
            r0.zze(r2, r3);	 Catch:{ all -> 0x00cb }
            r0 = r6.zzf();	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x005b;
        L_0x003a:
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzw;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r2.componentName;	 Catch:{ all -> 0x00cb }
            r2 = r2.getClassName();	 Catch:{ all -> 0x00cb }
            r0 = r0.zzf(r2);	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x005b;
        L_0x0050:
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzt;	 Catch:{ all -> 0x00cb }
            r0.stopSelf(r2);	 Catch:{ all -> 0x00cb }
        L_0x005b:
            monitor-exit(r1);	 Catch:{ all -> 0x00cb }
        L_0x005c:
            return;
        L_0x005d:
            r0 = r6.zzf();	 Catch:{ RemoteException -> 0x00d4 }
            if (r0 == 0) goto L_0x00ce;
        L_0x0063:
            r0 = r6.zzab;	 Catch:{ RemoteException -> 0x00d4 }
            r2 = android.os.Message.obtain();	 Catch:{ RemoteException -> 0x00d4 }
            r3 = 3;
            r2.what = r3;	 Catch:{ RemoteException -> 0x00d4 }
            r2.arg1 = r7;	 Catch:{ RemoteException -> 0x00d4 }
            r3 = new android.os.Bundle;	 Catch:{ RemoteException -> 0x00d4 }
            r3.<init>();	 Catch:{ RemoteException -> 0x00d4 }
            r4 = "component";
            r5 = r6.zzy;	 Catch:{ RemoteException -> 0x00d4 }
            r5 = r5.componentName;	 Catch:{ RemoteException -> 0x00d4 }
            r3.putParcelable(r4, r5);	 Catch:{ RemoteException -> 0x00d4 }
            r4 = "tag";
            r5 = r6.tag;	 Catch:{ RemoteException -> 0x00d4 }
            r3.putString(r4, r5);	 Catch:{ RemoteException -> 0x00d4 }
            r2.setData(r3);	 Catch:{ RemoteException -> 0x00d4 }
            r0.send(r2);	 Catch:{ RemoteException -> 0x00d4 }
        L_0x008d:
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzw;	 Catch:{ all -> 0x00cb }
            r2 = r6.tag;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r3 = r3.componentName;	 Catch:{ all -> 0x00cb }
            r3 = r3.getClassName();	 Catch:{ all -> 0x00cb }
            r0.zze(r2, r3);	 Catch:{ all -> 0x00cb }
            r0 = r6.zzf();	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9;
        L_0x00a8:
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzw;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r2.componentName;	 Catch:{ all -> 0x00cb }
            r2 = r2.getClassName();	 Catch:{ all -> 0x00cb }
            r0 = r0.zzf(r2);	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9;
        L_0x00be:
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzt;	 Catch:{ all -> 0x00cb }
            r0.stopSelf(r2);	 Catch:{ all -> 0x00cb }
        L_0x00c9:
            monitor-exit(r1);	 Catch:{ all -> 0x00cb }
            goto L_0x005c;
        L_0x00cb:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x00cb }
            throw r0;
        L_0x00ce:
            r0 = r6.zzaa;	 Catch:{ RemoteException -> 0x00d4 }
            r0.zzf(r7);	 Catch:{ RemoteException -> 0x00d4 }
            goto L_0x008d;
        L_0x00d4:
            r0 = move-exception;
            r2 = "GcmTaskService";
            r3 = "Error reporting result of operation to scheduler for ";
            r0 = r6.tag;	 Catch:{ all -> 0x0131 }
            r0 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0131 }
            r4 = r0.length();	 Catch:{ all -> 0x0131 }
            if (r4 == 0) goto L_0x012b;
        L_0x00e7:
            r0 = r3.concat(r0);	 Catch:{ all -> 0x0131 }
        L_0x00eb:
            android.util.Log.e(r2, r0);	 Catch:{ all -> 0x0131 }
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzw;	 Catch:{ all -> 0x00cb }
            r2 = r6.tag;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r3 = r3.componentName;	 Catch:{ all -> 0x00cb }
            r3 = r3.getClassName();	 Catch:{ all -> 0x00cb }
            r0.zze(r2, r3);	 Catch:{ all -> 0x00cb }
            r0 = r6.zzf();	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9;
        L_0x0109:
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r0 = r0.zzw;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r2.componentName;	 Catch:{ all -> 0x00cb }
            r2 = r2.getClassName();	 Catch:{ all -> 0x00cb }
            r0 = r0.zzf(r2);	 Catch:{ all -> 0x00cb }
            if (r0 != 0) goto L_0x00c9;
        L_0x011f:
            r0 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzt;	 Catch:{ all -> 0x00cb }
            r0.stopSelf(r2);	 Catch:{ all -> 0x00cb }
            goto L_0x00c9;
        L_0x012b:
            r0 = new java.lang.String;	 Catch:{ all -> 0x0131 }
            r0.<init>(r3);	 Catch:{ all -> 0x0131 }
            goto L_0x00eb;
        L_0x0131:
            r0 = move-exception;
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzw;	 Catch:{ all -> 0x00cb }
            r3 = r6.tag;	 Catch:{ all -> 0x00cb }
            r4 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r4 = r4.componentName;	 Catch:{ all -> 0x00cb }
            r4 = r4.getClassName();	 Catch:{ all -> 0x00cb }
            r2.zze(r3, r4);	 Catch:{ all -> 0x00cb }
            r2 = r6.zzf();	 Catch:{ all -> 0x00cb }
            if (r2 != 0) goto L_0x016e;
        L_0x014d:
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r2 = r2.zzw;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r3 = r3.componentName;	 Catch:{ all -> 0x00cb }
            r3 = r3.getClassName();	 Catch:{ all -> 0x00cb }
            r2 = r2.zzf(r3);	 Catch:{ all -> 0x00cb }
            if (r2 != 0) goto L_0x016e;
        L_0x0163:
            r2 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r3 = r6.zzy;	 Catch:{ all -> 0x00cb }
            r3 = r3.zzt;	 Catch:{ all -> 0x00cb }
            r2.stopSelf(r3);	 Catch:{ all -> 0x00cb }
        L_0x016e:
            throw r0;	 Catch:{ all -> 0x00cb }
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.GcmTaskService.zze.zze(int):void");
        }

        private final boolean zzf() {
            return this.zzab != null;
        }

        public final void run() {
            zze(this.zzy.onRunTask(new TaskParams(this.tag, this.extras, this.zzz)));
        }
    }

    private final void zzd(int i) {
        synchronized (this.lock) {
            this.zzt = i;
            if (!this.zzw.zzf(this.componentName.getClassName())) {
                stopSelf(this.zzt);
            }
        }
    }

    private final void zzd(zze zze) {
        try {
            this.zzu.execute(zze);
        } catch (Throwable e) {
            Log.e("GcmTaskService", "Executor is shutdown. onDestroy was called but main looper had an unprocessed start task message. The task will be retried with backoff delay.", e);
            zze.zze(1);
        }
    }

    private final boolean zzg(String str) {
        boolean z;
        synchronized (this.lock) {
            z = !this.zzw.zzd(str, this.componentName.getClassName());
            if (z) {
                String packageName = getPackageName();
                Log.w("GcmTaskService", new StringBuilder((String.valueOf(packageName).length() + 44) + String.valueOf(str).length()).append(packageName).append(" ").append(str).append(": Task already running, won't start another").toString());
            }
        }
        return z;
    }

    public IBinder onBind(Intent intent) {
        return (intent != null && PlatformVersion.isAtLeastLollipop() && SERVICE_ACTION_EXECUTE_TASK.equals(intent.getAction())) ? this.zzv.getBinder() : null;
    }

    public void onCreate() {
        super.onCreate();
        this.zzw = GcmNetworkManager.getInstance(this);
        this.zzu = Executors.newFixedThreadPool(2, new zze(this));
        this.zzv = new Messenger(new zzd(this, Looper.getMainLooper()));
        this.componentName = new ComponentName(this, getClass());
    }

    public void onDestroy() {
        super.onDestroy();
        List shutdownNow = this.zzu.shutdownNow();
        if (!shutdownNow.isEmpty()) {
            Log.e("GcmTaskService", "Shutting down, but not all tasks are finished executing. Remaining: " + shutdownNow.size());
        }
    }

    public void onInitializeTasks() {
    }

    public abstract int onRunTask(TaskParams taskParams);

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            zzd(i2);
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
                    } else if (zzg(stringExtra)) {
                        zzd(i2);
                    } else {
                        zzd(new zze(this, stringExtra, ((PendingCallback) parcelableExtra).zzal, bundleExtra, parcelableArrayListExtra));
                    }
                } else if (SERVICE_ACTION_INITIALIZE.equals(action)) {
                    onInitializeTasks();
                } else {
                    Log.e("GcmTaskService", new StringBuilder(String.valueOf(action).length() + 37).append("Unknown action received ").append(action).append(", terminating").toString());
                }
                zzd(i2);
            } finally {
                zzd(i2);
            }
        }
        return 2;
    }
}
