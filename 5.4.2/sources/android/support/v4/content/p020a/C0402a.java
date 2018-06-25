package android.support.v4.content.p020a;

import android.content.res.Resources;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.content.a.a */
public final class C0402a {
    /* renamed from: a */
    private static final C0398a f1163a;

    /* renamed from: android.support.v4.content.a.a$a */
    private interface C0398a {
        /* renamed from: a */
        int mo314a(Resources resources);

        /* renamed from: b */
        int mo315b(Resources resources);

        /* renamed from: c */
        int mo316c(Resources resources);
    }

    /* renamed from: android.support.v4.content.a.a$b */
    private static class C0399b implements C0398a {
        C0399b() {
        }

        /* renamed from: a */
        public int mo314a(Resources resources) {
            return C0403b.m1863a(resources);
        }

        /* renamed from: b */
        public int mo315b(Resources resources) {
            return C0403b.m1864b(resources);
        }

        /* renamed from: c */
        public int mo316c(Resources resources) {
            return C0403b.m1865c(resources);
        }
    }

    /* renamed from: android.support.v4.content.a.a$c */
    private static class C0400c extends C0399b {
        C0400c() {
        }

        /* renamed from: a */
        public int mo314a(Resources resources) {
            return C0404c.m1866a(resources);
        }

        /* renamed from: b */
        public int mo315b(Resources resources) {
            return C0404c.m1867b(resources);
        }

        /* renamed from: c */
        public int mo316c(Resources resources) {
            return C0404c.m1868c(resources);
        }
    }

    /* renamed from: android.support.v4.content.a.a$d */
    private static class C0401d extends C0400c {
        C0401d() {
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 17) {
            f1163a = new C0401d();
        } else if (i >= 13) {
            f1163a = new C0400c();
        } else {
            f1163a = new C0399b();
        }
    }

    /* renamed from: a */
    public static int m1860a(Resources resources) {
        return f1163a.mo314a(resources);
    }

    /* renamed from: b */
    public static int m1861b(Resources resources) {
        return f1163a.mo315b(resources);
    }

    /* renamed from: c */
    public static int m1862c(Resources resources) {
        return f1163a.mo316c(resources);
    }
}
