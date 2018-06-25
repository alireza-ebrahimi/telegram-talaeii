package android.support.p010c.p012b.p013a;

import android.annotation.TargetApi;
import android.content.ClipDescription;
import android.net.Uri;
import android.support.v4.p014d.C0432c;

@TargetApi(13)
/* renamed from: android.support.c.b.a.e */
public final class C0060e {
    /* renamed from: a */
    private final C0057c f124a;

    /* renamed from: android.support.c.b.a.e$c */
    private interface C0057c {
        /* renamed from: a */
        Uri mo37a();

        /* renamed from: b */
        ClipDescription mo38b();

        /* renamed from: c */
        void mo39c();

        /* renamed from: d */
        void mo40d();
    }

    /* renamed from: android.support.c.b.a.e$a */
    private static final class C0058a implements C0057c {
        /* renamed from: a */
        final Object f120a;

        public C0058a(Uri uri, ClipDescription clipDescription, Uri uri2) {
            this.f120a = C0061f.m156a(uri, clipDescription, uri2);
        }

        public C0058a(Object obj) {
            this.f120a = obj;
        }

        /* renamed from: a */
        public Uri mo37a() {
            return C0061f.m155a(this.f120a);
        }

        /* renamed from: b */
        public ClipDescription mo38b() {
            return C0061f.m157b(this.f120a);
        }

        /* renamed from: c */
        public void mo39c() {
            C0061f.m158c(this.f120a);
        }

        /* renamed from: d */
        public void mo40d() {
            C0061f.m159d(this.f120a);
        }
    }

    /* renamed from: android.support.c.b.a.e$b */
    private static final class C0059b implements C0057c {
        /* renamed from: a */
        private final Uri f121a;
        /* renamed from: b */
        private final ClipDescription f122b;
        /* renamed from: c */
        private final Uri f123c;

        public C0059b(Uri uri, ClipDescription clipDescription, Uri uri2) {
            this.f121a = uri;
            this.f122b = clipDescription;
            this.f123c = uri2;
        }

        /* renamed from: a */
        public Uri mo37a() {
            return this.f121a;
        }

        /* renamed from: b */
        public ClipDescription mo38b() {
            return this.f122b;
        }

        /* renamed from: c */
        public void mo39c() {
        }

        /* renamed from: d */
        public void mo40d() {
        }
    }

    public C0060e(Uri uri, ClipDescription clipDescription, Uri uri2) {
        if (C0432c.m1913b()) {
            this.f124a = new C0058a(uri, clipDescription, uri2);
        } else {
            this.f124a = new C0059b(uri, clipDescription, uri2);
        }
    }

    private C0060e(C0057c c0057c) {
        this.f124a = c0057c;
    }

    /* renamed from: a */
    public static C0060e m150a(Object obj) {
        return (obj != null && C0432c.m1913b()) ? new C0060e(new C0058a(obj)) : null;
    }

    /* renamed from: a */
    public Uri m151a() {
        return this.f124a.mo37a();
    }

    /* renamed from: b */
    public ClipDescription m152b() {
        return this.f124a.mo38b();
    }

    /* renamed from: c */
    public void m153c() {
        this.f124a.mo39c();
    }

    /* renamed from: d */
    public void m154d() {
        this.f124a.mo40d();
    }
}
