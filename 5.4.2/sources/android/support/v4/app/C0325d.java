package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

@TargetApi(16)
/* renamed from: android.support.v4.app.d */
class C0325d {
    /* renamed from: a */
    public static void m1433a(Activity activity) {
        activity.finishAffinity();
    }

    /* renamed from: a */
    public static void m1434a(Activity activity, Intent intent, int i, Bundle bundle) {
        activity.startActivityForResult(intent, i, bundle);
    }

    /* renamed from: a */
    public static void m1435a(Activity activity, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        activity.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }
}
