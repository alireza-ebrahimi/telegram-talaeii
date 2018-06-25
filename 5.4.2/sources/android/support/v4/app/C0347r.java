package android.support.v4.app;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.IBinder;

@TargetApi(18)
/* renamed from: android.support.v4.app.r */
class C0347r {
    /* renamed from: a */
    public static IBinder m1488a(Bundle bundle, String str) {
        return bundle.getBinder(str);
    }

    /* renamed from: a */
    public static void m1489a(Bundle bundle, String str, IBinder iBinder) {
        bundle.putBinder(str, iBinder);
    }
}
