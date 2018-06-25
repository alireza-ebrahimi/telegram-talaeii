package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.v4.content.C0429p;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import javax.annotation.concurrent.GuardedBy;

public final class FirebaseInstanceIdReceiver extends C0429p {
    /* renamed from: a */
    private static boolean f5660a = false;
    @GuardedBy("FirebaseInstanceIdReceiver.class")
    /* renamed from: b */
    private static ac f5661b;
    @GuardedBy("FirebaseInstanceIdReceiver.class")
    /* renamed from: c */
    private static ac f5662c;

    /* renamed from: a */
    private static synchronized ac m8782a(Context context, String str) {
        ac acVar;
        synchronized (FirebaseInstanceIdReceiver.class) {
            if ("com.google.firebase.MESSAGING_EVENT".equals(str)) {
                if (f5662c == null) {
                    f5662c = new ac(context, str);
                }
                acVar = f5662c;
            } else {
                if (f5661b == null) {
                    f5661b = new ac(context, str);
                }
                acVar = f5661b;
            }
        }
        return acVar;
    }

    /* renamed from: a */
    private final void m8783a(Context context, Intent intent, String str) {
        String str2 = null;
        int i = 0;
        int i2 = -1;
        intent.setComponent(null);
        intent.setPackage(context.getPackageName());
        if (VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        String stringExtra = intent.getStringExtra("gcm.rawData64");
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra("gcm.rawData64");
        }
        if ("google.com/iid".equals(intent.getStringExtra("from")) || "com.google.firebase.INSTANCE_ID_EVENT".equals(str)) {
            str2 = "com.google.firebase.INSTANCE_ID_EVENT";
        } else if ("com.google.android.c2dm.intent.RECEIVE".equals(str) || "com.google.firebase.MESSAGING_EVENT".equals(str)) {
            str2 = "com.google.firebase.MESSAGING_EVENT";
        } else {
            Log.d("FirebaseInstanceId", "Unexpected intent");
        }
        if (str2 != null) {
            if (PlatformVersion.isAtLeastO() && context.getApplicationInfo().targetSdkVersion >= 26) {
                i = 1;
            }
            if (i != 0) {
                if (isOrderedBroadcast()) {
                    setResultCode(-1);
                }
                m8782a(context, str2).m8796a(intent, goAsync());
            } else {
                i2 = C1945q.m8872a().m8873a(context, str2, intent);
            }
        }
        if (isOrderedBroadcast()) {
            setResultCode(i2);
        }
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Parcelable parcelableExtra = intent.getParcelableExtra("wrapped_intent");
            if (parcelableExtra instanceof Intent) {
                m8783a(context, (Intent) parcelableExtra, intent.getAction());
            } else {
                m8783a(context, intent, intent.getAction());
            }
        }
    }
}
