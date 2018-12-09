package android.support.design.widget;

import android.support.design.widget.C0201w.C0154a;
import android.support.design.widget.C0201w.C0155b;
import android.util.StateSet;
import java.util.ArrayList;

/* renamed from: android.support.design.widget.r */
final class C0177r {
    /* renamed from: a */
    C0201w f598a = null;
    /* renamed from: b */
    private final ArrayList<C0176a> f599b = new ArrayList();
    /* renamed from: c */
    private C0176a f600c = null;
    /* renamed from: d */
    private final C0154a f601d = new C01751(this);

    /* renamed from: android.support.design.widget.r$1 */
    class C01751 extends C0155b {
        /* renamed from: a */
        final /* synthetic */ C0177r f595a;

        C01751(C0177r c0177r) {
            this.f595a = c0177r;
        }

        /* renamed from: b */
        public void mo141b(C0201w c0201w) {
            if (this.f595a.f598a == c0201w) {
                this.f595a.f598a = null;
            }
        }
    }

    /* renamed from: android.support.design.widget.r$a */
    static class C0176a {
        /* renamed from: a */
        final int[] f596a;
        /* renamed from: b */
        final C0201w f597b;

        C0176a(int[] iArr, C0201w c0201w) {
            this.f596a = iArr;
            this.f597b = c0201w;
        }
    }

    C0177r() {
    }

    /* renamed from: a */
    private void m839a(C0176a c0176a) {
        this.f598a = c0176a.f597b;
        this.f598a.m939a();
    }

    /* renamed from: b */
    private void m840b() {
        if (this.f598a != null) {
            this.f598a.m949e();
            this.f598a = null;
        }
    }

    /* renamed from: a */
    public void m841a() {
        if (this.f598a != null) {
            this.f598a.m951g();
            this.f598a = null;
        }
    }

    /* renamed from: a */
    void m842a(int[] iArr) {
        C0176a c0176a;
        int size = this.f599b.size();
        for (int i = 0; i < size; i++) {
            c0176a = (C0176a) this.f599b.get(i);
            if (StateSet.stateSetMatches(c0176a.f596a, iArr)) {
                break;
            }
        }
        c0176a = null;
        if (c0176a != this.f600c) {
            if (this.f600c != null) {
                m840b();
            }
            this.f600c = c0176a;
            if (c0176a != null) {
                m839a(c0176a);
            }
        }
    }

    /* renamed from: a */
    public void m843a(int[] iArr, C0201w c0201w) {
        C0176a c0176a = new C0176a(iArr, c0201w);
        c0201w.m943a(this.f601d);
        this.f599b.add(c0176a);
    }
}
