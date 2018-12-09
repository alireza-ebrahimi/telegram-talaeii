package android.support.design.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: android.support.design.widget.z */
class C0210z {
    /* renamed from: a */
    private static final C0207a f700a;

    /* renamed from: android.support.design.widget.z$a */
    private interface C0207a {
        /* renamed from: a */
        void mo189a(ViewGroup viewGroup, View view, Rect rect);
    }

    /* renamed from: android.support.design.widget.z$b */
    private static class C0208b implements C0207a {
        C0208b() {
        }

        /* renamed from: a */
        public void mo189a(ViewGroup viewGroup, View view, Rect rect) {
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
            rect.offset(view.getScrollX(), view.getScrollY());
        }
    }

    /* renamed from: android.support.design.widget.z$c */
    private static class C0209c implements C0207a {
        C0209c() {
        }

        /* renamed from: a */
        public void mo189a(ViewGroup viewGroup, View view, Rect rect) {
            aa.m649a(viewGroup, view, rect);
        }
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            f700a = new C0209c();
        } else {
            f700a = new C0208b();
        }
    }

    /* renamed from: a */
    static void m990a(ViewGroup viewGroup, View view, Rect rect) {
        f700a.mo189a(viewGroup, view, rect);
    }

    /* renamed from: b */
    static void m991b(ViewGroup viewGroup, View view, Rect rect) {
        rect.set(0, 0, view.getWidth(), view.getHeight());
        C0210z.m990a(viewGroup, view, rect);
    }
}
