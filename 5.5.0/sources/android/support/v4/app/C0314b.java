package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;

@TargetApi(23)
/* renamed from: android.support.v4.app.b */
class C0314b {

    /* renamed from: android.support.v4.app.b$a */
    public interface C0313a {
        /* renamed from: a */
        void mo272a(int i);
    }

    /* renamed from: a */
    public static void m1411a(Activity activity, String[] strArr, int i) {
        if (activity instanceof C0313a) {
            ((C0313a) activity).mo272a(i);
        }
        activity.requestPermissions(strArr, i);
    }

    /* renamed from: a */
    public static boolean m1412a(Activity activity, String str) {
        return activity.shouldShowRequestPermissionRationale(str);
    }
}
