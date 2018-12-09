package android.support.v4.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;

/* renamed from: android.support.v4.app.e */
public class C0326e {

    @TargetApi(21)
    /* renamed from: android.support.v4.app.e$a */
    private static class C0327a extends C0326e {
        /* renamed from: a */
        private final C0331f f1022a;

        C0327a(C0331f c0331f) {
            this.f1022a = c0331f;
        }

        /* renamed from: a */
        public Bundle mo241a() {
            return this.f1022a.m1443a();
        }
    }

    @TargetApi(23)
    /* renamed from: android.support.v4.app.e$b */
    private static class C0328b extends C0326e {
        /* renamed from: a */
        private final C0332g f1023a;

        C0328b(C0332g c0332g) {
            this.f1023a = c0332g;
        }

        /* renamed from: a */
        public Bundle mo241a() {
            return this.f1023a.m1445a();
        }
    }

    @TargetApi(24)
    /* renamed from: android.support.v4.app.e$c */
    private static class C0329c extends C0326e {
        /* renamed from: a */
        private final C0333h f1024a;

        C0329c(C0333h c0333h) {
            this.f1024a = c0333h;
        }

        /* renamed from: a */
        public Bundle mo241a() {
            return this.f1024a.m1447a();
        }
    }

    @TargetApi(16)
    /* renamed from: android.support.v4.app.e$d */
    private static class C0330d extends C0326e {
        /* renamed from: a */
        private final C0334i f1025a;

        C0330d(C0334i c0334i) {
            this.f1025a = c0334i;
        }

        /* renamed from: a */
        public Bundle mo241a() {
            return this.f1025a.m1449a();
        }
    }

    protected C0326e() {
    }

    /* renamed from: a */
    public static C0326e m1436a(Context context, int i, int i2) {
        return VERSION.SDK_INT >= 24 ? new C0329c(C0333h.m1446a(context, i, i2)) : VERSION.SDK_INT >= 23 ? new C0328b(C0332g.m1444a(context, i, i2)) : VERSION.SDK_INT >= 21 ? new C0327a(C0331f.m1442a(context, i, i2)) : VERSION.SDK_INT >= 16 ? new C0330d(C0334i.m1448a(context, i, i2)) : new C0326e();
    }

    /* renamed from: a */
    public Bundle mo241a() {
        return null;
    }
}
