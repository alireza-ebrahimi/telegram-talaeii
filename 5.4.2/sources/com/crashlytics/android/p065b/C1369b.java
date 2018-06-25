package com.crashlytics.android.p065b;

import android.annotation.TargetApi;
import android.app.Activity;
import java.util.concurrent.ExecutorService;
import p033b.p034a.p035a.p036a.C1223a;
import p033b.p034a.p035a.p036a.C1223a.C1091b;

@TargetApi(14)
/* renamed from: com.crashlytics.android.b.b */
class C1369b extends C1366a {
    /* renamed from: a */
    private final C1091b f4144a = new C13681(this);
    /* renamed from: b */
    private final ExecutorService f4145b;

    /* renamed from: com.crashlytics.android.b.b$1 */
    class C13681 extends C1091b {
        /* renamed from: a */
        final /* synthetic */ C1369b f4143a;

        /* renamed from: com.crashlytics.android.b.b$1$1 */
        class C13671 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C13681 f4142a;

            C13671(C13681 c13681) {
                this.f4142a = c13681;
            }

            public void run() {
                this.f4142a.f4143a.m6932c();
            }
        }

        C13681(C1369b c1369b) {
            this.f4143a = c1369b;
        }

        /* renamed from: a */
        public void mo1071a(Activity activity) {
            if (this.f4143a.m6930a()) {
                this.f4143a.f4145b.submit(new C13671(this));
            }
        }
    }

    public C1369b(C1223a c1223a, ExecutorService executorService) {
        this.f4145b = executorService;
        c1223a.m6370a(this.f4144a);
    }
}
