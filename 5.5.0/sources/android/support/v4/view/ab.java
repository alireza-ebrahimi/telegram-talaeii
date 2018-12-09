package android.support.v4.view;

import android.content.Context;
import android.support.v4.p014d.C0432c;

public final class ab {
    /* renamed from: a */
    static final C0560c f1314a;
    /* renamed from: b */
    private Object f1315b;

    /* renamed from: android.support.v4.view.ab$c */
    interface C0560c {
        /* renamed from: a */
        Object mo438a(Context context, int i);
    }

    /* renamed from: android.support.v4.view.ab$b */
    static class C0561b implements C0560c {
        C0561b() {
        }

        /* renamed from: a */
        public Object mo438a(Context context, int i) {
            return null;
        }
    }

    /* renamed from: android.support.v4.view.ab$a */
    static class C0562a extends C0561b {
        C0562a() {
        }

        /* renamed from: a */
        public Object mo438a(Context context, int i) {
            return ac.m2509a(context, i);
        }
    }

    static {
        if (C0432c.m1912a()) {
            f1314a = new C0562a();
        } else {
            f1314a = new C0561b();
        }
    }

    private ab(Object obj) {
        this.f1315b = obj;
    }

    /* renamed from: a */
    public static ab m2507a(Context context, int i) {
        return new ab(f1314a.mo438a(context, i));
    }

    /* renamed from: a */
    public Object m2508a() {
        return this.f1315b;
    }
}
