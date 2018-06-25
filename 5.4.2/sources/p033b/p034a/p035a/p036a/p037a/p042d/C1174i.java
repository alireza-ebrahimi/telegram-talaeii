package p033b.p034a.p035a.p036a.p037a.p042d;

import android.content.Context;
import p033b.p034a.p035a.p036a.p037a.p039b.C1110i;

/* renamed from: b.a.a.a.a.d.i */
public class C1174i implements Runnable {
    /* renamed from: a */
    private final Context f3402a;
    /* renamed from: b */
    private final C1170e f3403b;

    public C1174i(Context context, C1170e c1170e) {
        this.f3402a = context;
        this.f3403b = c1170e;
    }

    public void run() {
        try {
            C1110i.m6008a(this.f3402a, "Performing time based file roll over.");
            if (!this.f3403b.mo1139c()) {
                this.f3403b.mo1140d();
            }
        } catch (Throwable e) {
            C1110i.m6009a(this.f3402a, "Failed to roll over file", e);
        }
    }
}
