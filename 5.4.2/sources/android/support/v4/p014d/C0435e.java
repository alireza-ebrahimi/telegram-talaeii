package android.support.v4.p014d;

import android.annotation.TargetApi;
import android.os.CancellationSignal;

@TargetApi(16)
/* renamed from: android.support.v4.d.e */
class C0435e {
    /* renamed from: a */
    public static Object m1917a() {
        return new CancellationSignal();
    }

    /* renamed from: a */
    public static void m1918a(Object obj) {
        ((CancellationSignal) obj).cancel();
    }
}
