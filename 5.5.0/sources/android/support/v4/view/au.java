package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

public final class au {
    /* renamed from: a */
    static final C0585b f1331a;

    /* renamed from: android.support.v4.view.au$b */
    interface C0585b {
        /* renamed from: a */
        void mo521a(ViewParent viewParent, View view);

        /* renamed from: a */
        void mo522a(ViewParent viewParent, View view, int i, int i2, int i3, int i4);

        /* renamed from: a */
        void mo523a(ViewParent viewParent, View view, int i, int i2, int[] iArr);

        /* renamed from: a */
        boolean mo524a(ViewParent viewParent, View view, float f, float f2);

        /* renamed from: a */
        boolean mo525a(ViewParent viewParent, View view, float f, float f2, boolean z);

        /* renamed from: a */
        boolean mo526a(ViewParent viewParent, View view, View view2, int i);

        /* renamed from: a */
        boolean mo527a(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: b */
        void mo528b(ViewParent viewParent, View view, View view2, int i);
    }

    /* renamed from: android.support.v4.view.au$e */
    static class C0586e implements C0585b {
        C0586e() {
        }

        /* renamed from: a */
        public void mo521a(ViewParent viewParent, View view) {
            if (viewParent instanceof C0107x) {
                ((C0107x) viewParent).onStopNestedScroll(view);
            }
        }

        /* renamed from: a */
        public void mo522a(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
            if (viewParent instanceof C0107x) {
                ((C0107x) viewParent).onNestedScroll(view, i, i2, i3, i4);
            }
        }

        /* renamed from: a */
        public void mo523a(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
            if (viewParent instanceof C0107x) {
                ((C0107x) viewParent).onNestedPreScroll(view, i, i2, iArr);
            }
        }

        /* renamed from: a */
        public boolean mo524a(ViewParent viewParent, View view, float f, float f2) {
            return viewParent instanceof C0107x ? ((C0107x) viewParent).onNestedPreFling(view, f, f2) : false;
        }

        /* renamed from: a */
        public boolean mo525a(ViewParent viewParent, View view, float f, float f2, boolean z) {
            return viewParent instanceof C0107x ? ((C0107x) viewParent).onNestedFling(view, f, f2, z) : false;
        }

        /* renamed from: a */
        public boolean mo526a(ViewParent viewParent, View view, View view2, int i) {
            return viewParent instanceof C0107x ? ((C0107x) viewParent).onStartNestedScroll(view, view2, i) : false;
        }

        /* renamed from: a */
        public boolean mo527a(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
            if (view == null) {
                return false;
            }
            ((AccessibilityManager) view.getContext().getSystemService("accessibility")).sendAccessibilityEvent(accessibilityEvent);
            return true;
        }

        /* renamed from: b */
        public void mo528b(ViewParent viewParent, View view, View view2, int i) {
            if (viewParent instanceof C0107x) {
                ((C0107x) viewParent).onNestedScrollAccepted(view, view2, i);
            }
        }
    }

    /* renamed from: android.support.v4.view.au$a */
    static class C0587a extends C0586e {
        C0587a() {
        }

        /* renamed from: a */
        public boolean mo527a(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
            return av.m2968a(viewParent, view, accessibilityEvent);
        }
    }

    /* renamed from: android.support.v4.view.au$c */
    static class C0588c extends C0587a {
        C0588c() {
        }
    }

    /* renamed from: android.support.v4.view.au$d */
    static class C0589d extends C0588c {
        C0589d() {
        }

        /* renamed from: a */
        public void mo521a(ViewParent viewParent, View view) {
            aw.m2969a(viewParent, view);
        }

        /* renamed from: a */
        public void mo522a(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
            aw.m2970a(viewParent, view, i, i2, i3, i4);
        }

        /* renamed from: a */
        public void mo523a(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
            aw.m2971a(viewParent, view, i, i2, iArr);
        }

        /* renamed from: a */
        public boolean mo524a(ViewParent viewParent, View view, float f, float f2) {
            return aw.m2972a(viewParent, view, f, f2);
        }

        /* renamed from: a */
        public boolean mo525a(ViewParent viewParent, View view, float f, float f2, boolean z) {
            return aw.m2973a(viewParent, view, f, f2, z);
        }

        /* renamed from: a */
        public boolean mo526a(ViewParent viewParent, View view, View view2, int i) {
            return aw.m2974a(viewParent, view, view2, i);
        }

        /* renamed from: b */
        public void mo528b(ViewParent viewParent, View view, View view2, int i) {
            aw.m2975b(viewParent, view, view2, i);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 21) {
            f1331a = new C0589d();
        } else if (i >= 19) {
            f1331a = new C0588c();
        } else if (i >= 14) {
            f1331a = new C0587a();
        } else {
            f1331a = new C0586e();
        }
    }

    /* renamed from: a */
    public static void m2960a(ViewParent viewParent, View view) {
        f1331a.mo521a(viewParent, view);
    }

    /* renamed from: a */
    public static void m2961a(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
        f1331a.mo522a(viewParent, view, i, i2, i3, i4);
    }

    /* renamed from: a */
    public static void m2962a(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
        f1331a.mo523a(viewParent, view, i, i2, iArr);
    }

    /* renamed from: a */
    public static boolean m2963a(ViewParent viewParent, View view, float f, float f2) {
        return f1331a.mo524a(viewParent, view, f, f2);
    }

    /* renamed from: a */
    public static boolean m2964a(ViewParent viewParent, View view, float f, float f2, boolean z) {
        return f1331a.mo525a(viewParent, view, f, f2, z);
    }

    /* renamed from: a */
    public static boolean m2965a(ViewParent viewParent, View view, View view2, int i) {
        return f1331a.mo526a(viewParent, view, view2, i);
    }

    /* renamed from: a */
    public static boolean m2966a(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        return f1331a.mo527a(viewParent, view, accessibilityEvent);
    }

    /* renamed from: b */
    public static void m2967b(ViewParent viewParent, View view, View view2, int i) {
        f1331a.mo528b(viewParent, view, view2, i);
    }
}
