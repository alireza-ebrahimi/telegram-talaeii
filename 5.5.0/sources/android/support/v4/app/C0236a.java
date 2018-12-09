package android.support.v4.app;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.C0235a;

/* renamed from: android.support.v4.app.a */
public class C0236a extends C0235a {

    /* renamed from: android.support.v4.app.a$a */
    public interface C0234a {
        void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);
    }

    /* renamed from: a */
    public static void m1077a(Activity activity) {
        if (VERSION.SDK_INT >= 16) {
            C0325d.m1433a(activity);
        } else {
            activity.finish();
        }
    }

    /* renamed from: a */
    public static void m1078a(Activity activity, Intent intent, int i, Bundle bundle) {
        if (VERSION.SDK_INT >= 16) {
            C0325d.m1434a(activity, intent, i, bundle);
        } else {
            activity.startActivityForResult(intent, i);
        }
    }

    /* renamed from: a */
    public static void m1079a(Activity activity, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        if (VERSION.SDK_INT >= 16) {
            C0325d.m1435a(activity, intentSender, i, intent, i2, i3, i4, bundle);
        } else {
            activity.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4);
        }
    }

    /* renamed from: a */
    public static void m1080a(final Activity activity, final String[] strArr, final int i) {
        if (VERSION.SDK_INT >= 23) {
            C0314b.m1411a(activity, strArr, i);
        } else if (activity instanceof C0234a) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    int[] iArr = new int[strArr.length];
                    PackageManager packageManager = activity.getPackageManager();
                    String packageName = activity.getPackageName();
                    int length = strArr.length;
                    for (int i = 0; i < length; i++) {
                        iArr[i] = packageManager.checkPermission(strArr[i], packageName);
                    }
                    ((C0234a) activity).onRequestPermissionsResult(i, strArr, iArr);
                }
            });
        }
    }

    /* renamed from: a */
    public static boolean m1081a(Activity activity, String str) {
        return VERSION.SDK_INT >= 23 ? C0314b.m1412a(activity, str) : false;
    }
}
