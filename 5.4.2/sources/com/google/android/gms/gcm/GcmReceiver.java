package com.google.android.gms.gcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.support.v4.content.C0429p;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.iid.zzk;
import com.google.android.gms.wallet.WalletConstants;
import org.telegram.ui.ChatActivity;

@Deprecated
public class GcmReceiver extends C0429p {
    private static boolean zzq = false;
    private static zzk zzr;
    private static zzk zzs;

    private final synchronized zzk zzd(Context context, String str) {
        zzk zzk;
        if ("com.google.android.c2dm.intent.RECEIVE".equals(str)) {
            if (zzs == null) {
                zzs = new zzk(context, str);
            }
            zzk = zzs;
        } else {
            if (zzr == null) {
                zzr = new zzk(context, str);
            }
            zzk = zzr;
        }
        return zzk;
    }

    private final void zzd(Context context, Intent intent) {
        if (isOrderedBroadcast()) {
            setResultCode(ChatActivity.startAllServices);
        }
        ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
        if (resolveService == null || resolveService.serviceInfo == null) {
            Log.e("GcmReceiver", "Failed to resolve target intent service, skipping classname enforcement");
        } else {
            ServiceInfo serviceInfo = resolveService.serviceInfo;
            String str;
            String str2;
            if (!context.getPackageName().equals(serviceInfo.packageName) || serviceInfo.name == null) {
                str = serviceInfo.packageName;
                str2 = serviceInfo.name;
                Log.e("GcmReceiver", new StringBuilder((String.valueOf(str).length() + 94) + String.valueOf(str2).length()).append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ").append(str).append("/").append(str2).toString());
            } else {
                String valueOf;
                str2 = serviceInfo.name;
                if (str2.startsWith(".")) {
                    valueOf = String.valueOf(context.getPackageName());
                    str2 = String.valueOf(str2);
                    str2 = str2.length() != 0 ? valueOf.concat(str2) : new String(valueOf);
                }
                if (Log.isLoggable("GcmReceiver", 3)) {
                    str = "GcmReceiver";
                    String str3 = "Restricting intent to a specific service: ";
                    valueOf = String.valueOf(str2);
                    Log.d(str, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                }
                intent.setClassName(context.getPackageName(), str2);
            }
        }
        try {
            ComponentName startWakefulService;
            if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0) {
                startWakefulService = C0429p.startWakefulService(context, intent);
            } else {
                startWakefulService = context.startService(intent);
                Log.d("GcmReceiver", "Missing wake lock permission, service start may be delayed");
            }
            if (startWakefulService == null) {
                Log.e("GcmReceiver", "Error while delivering the message: ServiceIntent not found.");
                if (isOrderedBroadcast()) {
                    setResultCode(WalletConstants.ERROR_CODE_INVALID_PARAMETERS);
                }
            } else if (isOrderedBroadcast()) {
                setResultCode(-1);
            }
        } catch (Throwable e) {
            Log.e("GcmReceiver", "Error while delivering the message to the serviceIntent", e);
            if (isOrderedBroadcast()) {
                setResultCode(401);
            }
        }
    }

    public void onReceive(Context context, Intent intent) {
        int i = 0;
        if (Log.isLoggable("GcmReceiver", 3)) {
            Log.d("GcmReceiver", "received new intent");
        }
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        if ("google.com/iid".equals(intent.getStringExtra("from"))) {
            intent.setAction("com.google.android.gms.iid.InstanceID");
        }
        String stringExtra = intent.getStringExtra("gcm.rawData64");
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra("gcm.rawData64");
        }
        if (PlatformVersion.isAtLeastO() && context.getApplicationInfo().targetSdkVersion >= 26) {
            i = 1;
        }
        if (i != 0) {
            if (isOrderedBroadcast()) {
                setResultCode(-1);
            }
            zzd(context, intent.getAction()).zzd(intent, goAsync());
            return;
        }
        if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            zzd(context, intent);
        } else {
            zzd(context, intent);
        }
        if (isOrderedBroadcast() && getResultCode() == 0) {
            setResultCode(-1);
        }
    }
}
