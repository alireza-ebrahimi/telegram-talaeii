package android.support.v4.p017a;

import android.os.Build.VERSION;
import android.view.View;

/* renamed from: android.support.v4.a.a */
public final class C0211a {
    /* renamed from: a */
    private static final C0213c f701a;

    static {
        if (VERSION.SDK_INT >= 12) {
            f701a = new C0222f();
        } else {
            f701a = new C0218e();
        }
    }

    /* renamed from: a */
    public static C0216g m992a() {
        return f701a.mo197a();
    }

    /* renamed from: a */
    public static void m993a(View view) {
        f701a.mo198a(view);
    }
}
