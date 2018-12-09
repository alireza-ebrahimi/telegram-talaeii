package android.support.v4.app;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ba.C0312a;
import android.support.v4.app.ba.C0312a.C0305a;
import android.util.Log;

public final class ay extends C0312a {
    /* renamed from: a */
    public static final C0305a f1003a = new C03061();
    /* renamed from: g */
    private static final C0308b f1004g;
    /* renamed from: b */
    private final String f1005b;
    /* renamed from: c */
    private final CharSequence f1006c;
    /* renamed from: d */
    private final CharSequence[] f1007d;
    /* renamed from: e */
    private final boolean f1008e;
    /* renamed from: f */
    private final Bundle f1009f;

    /* renamed from: android.support.v4.app.ay$1 */
    static class C03061 implements C0305a {
        C03061() {
        }
    }

    /* renamed from: android.support.v4.app.ay$a */
    public static final class C0307a {
        /* renamed from: a */
        private final String f998a;
        /* renamed from: b */
        private CharSequence f999b;
        /* renamed from: c */
        private CharSequence[] f1000c;
        /* renamed from: d */
        private boolean f1001d = true;
        /* renamed from: e */
        private Bundle f1002e = new Bundle();

        public C0307a(String str) {
            if (str == null) {
                throw new IllegalArgumentException("Result key can't be null");
            }
            this.f998a = str;
        }

        /* renamed from: a */
        public C0307a m1391a(CharSequence charSequence) {
            this.f999b = charSequence;
            return this;
        }

        /* renamed from: a */
        public ay m1392a() {
            return new ay(this.f998a, this.f999b, this.f1000c, this.f1001d, this.f1002e);
        }
    }

    /* renamed from: android.support.v4.app.ay$b */
    interface C0308b {
        /* renamed from: a */
        Bundle mo235a(Intent intent);
    }

    /* renamed from: android.support.v4.app.ay$c */
    static class C0309c implements C0308b {
        C0309c() {
        }

        /* renamed from: a */
        public Bundle mo235a(Intent intent) {
            return az.m1408a(intent);
        }
    }

    /* renamed from: android.support.v4.app.ay$d */
    static class C0310d implements C0308b {
        C0310d() {
        }

        /* renamed from: a */
        public Bundle mo235a(Intent intent) {
            Log.w("RemoteInput", "RemoteInput is only supported from API Level 16");
            return null;
        }
    }

    /* renamed from: android.support.v4.app.ay$e */
    static class C0311e implements C0308b {
        C0311e() {
        }

        /* renamed from: a */
        public Bundle mo235a(Intent intent) {
            return bb.m1413a(intent);
        }
    }

    static {
        if (VERSION.SDK_INT >= 20) {
            f1004g = new C0309c();
        } else if (VERSION.SDK_INT >= 16) {
            f1004g = new C0311e();
        } else {
            f1004g = new C0310d();
        }
    }

    ay(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle) {
        this.f1005b = str;
        this.f1006c = charSequence;
        this.f1007d = charSequenceArr;
        this.f1008e = z;
        this.f1009f = bundle;
    }

    /* renamed from: a */
    public static Bundle m1402a(Intent intent) {
        return f1004g.mo235a(intent);
    }

    /* renamed from: a */
    public String mo236a() {
        return this.f1005b;
    }

    /* renamed from: b */
    public CharSequence mo237b() {
        return this.f1006c;
    }

    /* renamed from: c */
    public CharSequence[] mo238c() {
        return this.f1007d;
    }

    /* renamed from: d */
    public boolean mo239d() {
        return this.f1008e;
    }

    /* renamed from: e */
    public Bundle mo240e() {
        return this.f1009f;
    }
}
