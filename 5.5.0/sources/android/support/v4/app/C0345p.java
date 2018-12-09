package android.support.v4.app;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;

/* renamed from: android.support.v4.app.p */
public final class C0345p {
    /* renamed from: a */
    public static IBinder m1484a(Bundle bundle, String str) {
        return VERSION.SDK_INT >= 18 ? C0347r.m1488a(bundle, str) : C0346q.m1486a(bundle, str);
    }

    /* renamed from: a */
    public static void m1485a(Bundle bundle, String str, IBinder iBinder) {
        if (VERSION.SDK_INT >= 18) {
            C0347r.m1489a(bundle, str, iBinder);
        } else {
            C0346q.m1487a(bundle, str, iBinder);
        }
    }
}
