package com.p096g.p097a;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Map;

/* renamed from: com.g.a.m */
public class C1636m {
    /* renamed from: a */
    static final Handler f4988a = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            List list;
            int size;
            int i;
            switch (message.what) {
                case 3:
                    C1607a c1607a = (C1607a) message.obj;
                    if (c1607a.m7959g().f4998k) {
                        C1648v.m8064a("Main", "canceled", c1607a.f4914b.m8028a(), "target got garbage collected");
                    }
                    c1607a.f4913a.m8011a(c1607a.m7955c());
                    return;
                case 8:
                    list = (List) message.obj;
                    size = list.size();
                    for (i = 0; i < size; i++) {
                        C1615b c1615b = (C1615b) list.get(i);
                        c1615b.f4934a.m8017a(c1615b);
                    }
                    return;
                case 13:
                    list = (List) message.obj;
                    size = list.size();
                    for (i = 0; i < size; i++) {
                        C1607a c1607a2 = (C1607a) list.get(i);
                        c1607a2.f4913a.m8019c(c1607a2);
                    }
                    return;
                default:
                    throw new AssertionError("Unknown handler message received: " + message.what);
            }
        }
    };
    /* renamed from: b */
    static volatile C1636m f4989b = null;
    /* renamed from: c */
    final Context f4990c;
    /* renamed from: d */
    final C1620f f4991d;
    /* renamed from: e */
    final C1616c f4992e;
    /* renamed from: f */
    final C1643r f4993f;
    /* renamed from: g */
    final Map<Object, C1607a> f4994g;
    /* renamed from: h */
    final Map<ImageView, C1619e> f4995h;
    /* renamed from: i */
    final ReferenceQueue<Object> f4996i;
    /* renamed from: j */
    boolean f4997j;
    /* renamed from: k */
    volatile boolean f4998k;
    /* renamed from: l */
    private final C1631a f4999l;
    /* renamed from: m */
    private final C1634d f5000m;

    /* renamed from: com.g.a.m$a */
    public interface C1631a {
        /* renamed from: a */
        void m8006a(C1636m c1636m, Uri uri, Exception exception);
    }

    /* renamed from: com.g.a.m$b */
    public enum C1632b {
        MEMORY(-16711936),
        DISK(-16776961),
        NETWORK(-65536);
        
        /* renamed from: d */
        final int f4982d;

        private C1632b(int i) {
            this.f4982d = i;
        }
    }

    /* renamed from: com.g.a.m$c */
    public enum C1633c {
        LOW,
        NORMAL,
        HIGH
    }

    /* renamed from: com.g.a.m$d */
    public interface C1634d {
        /* renamed from: a */
        public static final C1634d f4987a = new C16351();

        /* renamed from: com.g.a.m$d$1 */
        static class C16351 implements C1634d {
            C16351() {
            }

            /* renamed from: a */
            public C1640o mo1250a(C1640o c1640o) {
                return c1640o;
            }
        }

        /* renamed from: a */
        C1640o mo1250a(C1640o c1640o);
    }

    /* renamed from: a */
    private void m8009a(Bitmap bitmap, C1632b c1632b, C1607a c1607a) {
        if (!c1607a.m7957e()) {
            if (!c1607a.m7958f()) {
                this.f4994g.remove(c1607a.m7955c());
            }
            if (bitmap == null) {
                c1607a.mo1247a();
                if (this.f4998k) {
                    C1648v.m8063a("Main", "errored", c1607a.f4914b.m8028a());
                }
            } else if (c1632b == null) {
                throw new AssertionError("LoadedFrom cannot be null.");
            } else {
                c1607a.mo1248a(bitmap, c1632b);
                if (this.f4998k) {
                    C1648v.m8064a("Main", "completed", c1607a.f4914b.m8028a(), "from " + c1632b);
                }
            }
        }
    }

    /* renamed from: a */
    private void m8011a(Object obj) {
        C1648v.m8061a();
        C1607a c1607a = (C1607a) this.f4994g.remove(obj);
        if (c1607a != null) {
            c1607a.mo1249b();
            this.f4991d.m7990b(c1607a);
        }
        if (obj instanceof ImageView) {
            C1619e c1619e = (C1619e) this.f4995h.remove((ImageView) obj);
            if (c1619e != null) {
                c1619e.m7987a();
            }
        }
    }

    /* renamed from: a */
    Bitmap m8012a(String str) {
        Bitmap a = this.f4992e.mo1245a(str);
        if (a != null) {
            this.f4993f.m8045a();
        } else {
            this.f4993f.m8048b();
        }
        return a;
    }

    /* renamed from: a */
    C1640o m8013a(C1640o c1640o) {
        C1640o a = this.f5000m.mo1250a(c1640o);
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("Request transformer " + this.f5000m.getClass().getCanonicalName() + " returned null for " + c1640o);
    }

    /* renamed from: a */
    public void m8014a(ImageView imageView) {
        m8011a((Object) imageView);
    }

    /* renamed from: a */
    void m8015a(ImageView imageView, C1619e c1619e) {
        this.f4995h.put(imageView, c1619e);
    }

    /* renamed from: a */
    void m8016a(C1607a c1607a) {
        Object c = c1607a.m7955c();
        if (!(c == null || this.f4994g.get(c) == c1607a)) {
            m8011a(c);
            this.f4994g.put(c, c1607a);
        }
        m8018b(c1607a);
    }

    /* renamed from: a */
    void m8017a(C1615b c1615b) {
        Object obj = 1;
        C1607a d = c1615b.m7975d();
        List e = c1615b.m7976e();
        Object obj2 = (e == null || e.isEmpty()) ? null : 1;
        if (d == null && obj2 == null) {
            obj = null;
        }
        if (obj != null) {
            Uri uri = c1615b.m7974c().f5028d;
            Exception f = c1615b.m7977f();
            Bitmap b = c1615b.m7973b();
            C1632b g = c1615b.m7978g();
            if (d != null) {
                m8009a(b, g, d);
            }
            if (obj2 != null) {
                int size = e.size();
                for (int i = 0; i < size; i++) {
                    m8009a(b, g, (C1607a) e.get(i));
                }
            }
            if (this.f4999l != null && f != null) {
                this.f4999l.m8006a(this, uri, f);
            }
        }
    }

    /* renamed from: b */
    void m8018b(C1607a c1607a) {
        this.f4991d.m7988a(c1607a);
    }

    /* renamed from: c */
    void m8019c(C1607a c1607a) {
        Bitmap bitmap = null;
        if (C1626j.m8004a(c1607a.f4917e)) {
            bitmap = m8012a(c1607a.m7956d());
        }
        if (bitmap != null) {
            m8009a(bitmap, C1632b.MEMORY, c1607a);
            if (this.f4998k) {
                C1648v.m8064a("Main", "completed", c1607a.f4914b.m8028a(), "from " + C1632b.MEMORY);
                return;
            }
            return;
        }
        m8016a(c1607a);
        if (this.f4998k) {
            C1648v.m8063a("Main", "resumed", c1607a.f4914b.m8028a());
        }
    }
}
