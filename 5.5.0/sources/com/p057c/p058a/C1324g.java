package com.p057c.p058a;

import org.p138a.p139a.C2434a;
import org.p138a.p139a.C2435b;

/* renamed from: com.c.a.g */
public class C1324g {
    /* renamed from: a */
    public static final /* synthetic */ C1324g f4002a = null;
    /* renamed from: b */
    private static /* synthetic */ Throwable f4003b;

    static {
        try {
            f4002a = new C1324g();
        } catch (Throwable th) {
            f4003b = th;
        }
    }

    /* renamed from: a */
    public static C1324g m6778a() {
        if (f4002a != null) {
            return f4002a;
        }
        throw new C2435b("com.googlecode.mp4parser.RequiresParseDetailAspect", f4003b);
    }

    /* renamed from: a */
    public void m6780a(C2434a c2434a) {
        if (!(c2434a.mo3392a() instanceof C1257a)) {
            throw new RuntimeException("Only methods in subclasses of " + C1257a.class.getName() + " can  be annotated with ParseDetail");
        } else if (!((C1257a) c2434a.mo3392a()).m6528n()) {
            ((C1257a) c2434a.mo3392a()).m6526l();
        }
    }
}
