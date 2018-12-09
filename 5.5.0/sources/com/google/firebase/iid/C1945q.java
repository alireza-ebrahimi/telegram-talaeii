package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.support.v4.content.C0429p;
import android.support.v4.p022f.C0463k;
import android.util.Log;
import com.google.android.gms.wallet.WalletConstants;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;
import org.telegram.ui.ChatActivity;

/* renamed from: com.google.firebase.iid.q */
public final class C1945q {
    /* renamed from: b */
    private static C1945q f5744b;
    /* renamed from: a */
    final Queue<Intent> f5745a = new ArrayDeque();
    @GuardedBy("serviceClassNames")
    /* renamed from: c */
    private final C0463k<String, String> f5746c = new C0463k();
    /* renamed from: d */
    private Boolean f5747d = null;
    /* renamed from: e */
    private final Queue<Intent> f5748e = new ArrayDeque();

    private C1945q() {
    }

    /* renamed from: a */
    private final int m8870a(Context context, Intent intent) {
        String str;
        ComponentName startWakefulService;
        synchronized (this.f5746c) {
            str = (String) this.f5746c.get(intent.getAction());
        }
        if (str == null) {
            ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
            if (resolveService == null || resolveService.serviceInfo == null) {
                Log.e("FirebaseInstanceId", "Failed to resolve target intent service, skipping classname enforcement");
                if (this.f5747d == null) {
                    this.f5747d = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
                }
                if (this.f5747d.booleanValue()) {
                    startWakefulService = C0429p.startWakefulService(context, intent);
                } else {
                    startWakefulService = context.startService(intent);
                    Log.d("FirebaseInstanceId", "Missing wake lock permission, service start may be delayed");
                }
                if (startWakefulService == null) {
                    return -1;
                }
                Log.e("FirebaseInstanceId", "Error while delivering the message: ServiceIntent not found.");
                return WalletConstants.ERROR_CODE_INVALID_PARAMETERS;
            }
            ServiceInfo serviceInfo = resolveService.serviceInfo;
            if (!context.getPackageName().equals(serviceInfo.packageName) || serviceInfo.name == null) {
                String str2 = serviceInfo.packageName;
                str = serviceInfo.name;
                Log.e("FirebaseInstanceId", new StringBuilder((String.valueOf(str2).length() + 94) + String.valueOf(str).length()).append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ").append(str2).append("/").append(str).toString());
                if (this.f5747d == null) {
                    if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0) {
                    }
                    this.f5747d = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
                }
                if (this.f5747d.booleanValue()) {
                    startWakefulService = context.startService(intent);
                    Log.d("FirebaseInstanceId", "Missing wake lock permission, service start may be delayed");
                } else {
                    startWakefulService = C0429p.startWakefulService(context, intent);
                }
                if (startWakefulService == null) {
                    return -1;
                }
                Log.e("FirebaseInstanceId", "Error while delivering the message: ServiceIntent not found.");
                return WalletConstants.ERROR_CODE_INVALID_PARAMETERS;
            }
            str = serviceInfo.name;
            if (str.startsWith(".")) {
                String valueOf = String.valueOf(context.getPackageName());
                str = String.valueOf(str);
                str = str.length() != 0 ? valueOf.concat(str) : new String(valueOf);
            }
            synchronized (this.f5746c) {
                this.f5746c.put(intent.getAction(), str);
            }
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            str2 = "FirebaseInstanceId";
            String str3 = "Restricting intent to a specific service: ";
            valueOf = String.valueOf(str);
            Log.d(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        }
        intent.setClassName(context.getPackageName(), str);
        try {
            if (this.f5747d == null) {
                if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0) {
                }
                this.f5747d = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
            }
            if (this.f5747d.booleanValue()) {
                startWakefulService = C0429p.startWakefulService(context, intent);
            } else {
                startWakefulService = context.startService(intent);
                Log.d("FirebaseInstanceId", "Missing wake lock permission, service start may be delayed");
            }
            if (startWakefulService == null) {
                return -1;
            }
            Log.e("FirebaseInstanceId", "Error while delivering the message: ServiceIntent not found.");
            return WalletConstants.ERROR_CODE_INVALID_PARAMETERS;
        } catch (Throwable e) {
            Log.e("FirebaseInstanceId", "Error while delivering the message to the serviceIntent", e);
            return 401;
        } catch (IllegalStateException e2) {
            str = String.valueOf(e2);
            Log.e("FirebaseInstanceId", new StringBuilder(String.valueOf(str).length() + 45).append("Failed to start service while in background: ").append(str).toString());
            return WalletConstants.ERROR_CODE_SERVICE_UNAVAILABLE;
        }
    }

    /* renamed from: a */
    public static PendingIntent m8871a(Context context, int i, Intent intent, int i2) {
        Intent intent2 = new Intent(context, FirebaseInstanceIdReceiver.class);
        intent2.setAction("com.google.firebase.MESSAGING_EVENT");
        intent2.putExtra("wrapped_intent", intent);
        return PendingIntent.getBroadcast(context, i, intent2, 1073741824);
    }

    /* renamed from: a */
    public static synchronized C1945q m8872a() {
        C1945q c1945q;
        synchronized (C1945q.class) {
            if (f5744b == null) {
                f5744b = new C1945q();
            }
            c1945q = f5744b;
        }
        return c1945q;
    }

    /* renamed from: a */
    public final int m8873a(Context context, String str, Intent intent) {
        Object obj = -1;
        switch (str.hashCode()) {
            case -842411455:
                if (str.equals("com.google.firebase.INSTANCE_ID_EVENT")) {
                    obj = null;
                    break;
                }
                break;
            case 41532704:
                if (str.equals("com.google.firebase.MESSAGING_EVENT")) {
                    obj = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                this.f5745a.offer(intent);
                break;
            case 1:
                this.f5748e.offer(intent);
                break;
            default:
                String str2 = "FirebaseInstanceId";
                String str3 = "Unknown service action: ";
                String valueOf = String.valueOf(str);
                Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                return ChatActivity.startAllServices;
        }
        Intent intent2 = new Intent(str);
        intent2.setPackage(context.getPackageName());
        return m8870a(context, intent2);
    }

    /* renamed from: b */
    public final Intent m8874b() {
        return (Intent) this.f5748e.poll();
    }
}
