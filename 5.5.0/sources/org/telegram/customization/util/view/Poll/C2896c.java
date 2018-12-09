package org.telegram.customization.util.view.Poll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* renamed from: org.telegram.customization.util.view.Poll.c */
public class C2896c {
    /* renamed from: a */
    String f9547a;
    /* renamed from: b */
    String f9548b;
    /* renamed from: c */
    ArrayList<C2894b> f9549c;
    /* renamed from: d */
    String f9550d;

    /* renamed from: org.telegram.customization.util.view.Poll.c$1 */
    class C28951 implements Comparator<C2894b> {
        /* renamed from: a */
        final /* synthetic */ C2896c f9546a;

        C28951(C2896c c2896c) {
            this.f9546a = c2896c;
        }

        /* renamed from: a */
        public int m13419a(C2894b c2894b, C2894b c2894b2) {
            return c2894b.m13418d() - c2894b2.m13418d();
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m13419a((C2894b) obj, (C2894b) obj2);
        }
    }

    /* renamed from: a */
    public String m13420a() {
        return this.f9550d;
    }

    /* renamed from: b */
    public String m13421b() {
        return this.f9548b;
    }

    /* renamed from: c */
    public String m13422c() {
        return this.f9547a;
    }

    /* renamed from: d */
    public ArrayList<C2894b> m13423d() {
        Collections.sort(this.f9549c, new C28951(this));
        return this.f9549c;
    }
}
