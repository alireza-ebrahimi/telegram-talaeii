package android.support.v4.content;

import android.os.Build.VERSION;
import java.util.concurrent.Executor;

/* renamed from: android.support.v4.content.n */
public final class C0427n {
    /* renamed from: a */
    public static Executor m1905a() {
        return VERSION.SDK_INT >= 11 ? C0413h.m1881a() : C0426m.f1187a;
    }
}
