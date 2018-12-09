package android.support.v4.view.p023a;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.p023a.C0548m.C0539a;
import android.support.v4.view.p023a.C0550n.C0543a;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.view.a.l */
public class C0546l {
    /* renamed from: a */
    private static final C0538a f1308a;
    /* renamed from: b */
    private final Object f1309b;

    /* renamed from: android.support.v4.view.a.l$a */
    interface C0538a {
        /* renamed from: a */
        Object mo418a(C0546l c0546l);
    }

    /* renamed from: android.support.v4.view.a.l$d */
    static class C0541d implements C0538a {
        C0541d() {
        }

        /* renamed from: a */
        public Object mo418a(C0546l c0546l) {
            return null;
        }
    }

    /* renamed from: android.support.v4.view.a.l$b */
    private static class C0542b extends C0541d {
        C0542b() {
        }

        /* renamed from: a */
        public Object mo418a(final C0546l c0546l) {
            return C0548m.m2427a(new C0539a(this) {
                /* renamed from: b */
                final /* synthetic */ C0542b f1305b;

                /* renamed from: a */
                public Object mo415a(int i) {
                    C0531e a = c0546l.mo583a(i);
                    return a == null ? null : a.m2304a();
                }

                /* renamed from: a */
                public List<Object> mo416a(String str, int i) {
                    List a = c0546l.m2424a(str, i);
                    if (a == null) {
                        return null;
                    }
                    List<Object> arrayList = new ArrayList();
                    int size = a.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        arrayList.add(((C0531e) a.get(i2)).m2304a());
                    }
                    return arrayList;
                }

                /* renamed from: a */
                public boolean mo417a(int i, int i2, Bundle bundle) {
                    return c0546l.mo584a(i, i2, bundle);
                }
            });
        }
    }

    /* renamed from: android.support.v4.view.a.l$c */
    private static class C0545c extends C0541d {
        C0545c() {
        }

        /* renamed from: a */
        public Object mo418a(final C0546l c0546l) {
            return C0550n.m2428a(new C0543a(this) {
                /* renamed from: b */
                final /* synthetic */ C0545c f1307b;

                /* renamed from: a */
                public Object mo419a(int i) {
                    C0531e a = c0546l.mo583a(i);
                    return a == null ? null : a.m2304a();
                }

                /* renamed from: a */
                public List<Object> mo420a(String str, int i) {
                    List a = c0546l.m2424a(str, i);
                    if (a == null) {
                        return null;
                    }
                    List<Object> arrayList = new ArrayList();
                    int size = a.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        arrayList.add(((C0531e) a.get(i2)).m2304a());
                    }
                    return arrayList;
                }

                /* renamed from: a */
                public boolean mo421a(int i, int i2, Bundle bundle) {
                    return c0546l.mo584a(i, i2, bundle);
                }

                /* renamed from: b */
                public Object mo422b(int i) {
                    C0531e b = c0546l.mo585b(i);
                    return b == null ? null : b.m2304a();
                }
            });
        }
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            f1308a = new C0545c();
        } else if (VERSION.SDK_INT >= 16) {
            f1308a = new C0542b();
        } else {
            f1308a = new C0541d();
        }
    }

    public C0546l() {
        this.f1309b = f1308a.mo418a(this);
    }

    public C0546l(Object obj) {
        this.f1309b = obj;
    }

    /* renamed from: a */
    public C0531e mo583a(int i) {
        return null;
    }

    /* renamed from: a */
    public Object m2423a() {
        return this.f1309b;
    }

    /* renamed from: a */
    public List<C0531e> m2424a(String str, int i) {
        return null;
    }

    /* renamed from: a */
    public boolean mo584a(int i, int i2, Bundle bundle) {
        return false;
    }

    /* renamed from: b */
    public C0531e mo585b(int i) {
        return null;
    }
}
