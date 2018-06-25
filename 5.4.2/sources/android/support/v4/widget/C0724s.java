package android.support.v4.widget;

import android.os.Build.VERSION;
import android.support.v4.view.C0625f;
import android.support.v4.view.ah;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* renamed from: android.support.v4.widget.s */
public final class C0724s {
    /* renamed from: a */
    static final C0719e f1623a;

    /* renamed from: android.support.v4.widget.s$e */
    interface C0719e {
        /* renamed from: a */
        void mo590a(PopupWindow popupWindow, int i);

        /* renamed from: a */
        void mo591a(PopupWindow popupWindow, View view, int i, int i2, int i3);

        /* renamed from: a */
        void mo592a(PopupWindow popupWindow, boolean z);
    }

    /* renamed from: android.support.v4.widget.s$c */
    static class C0720c implements C0719e {
        /* renamed from: a */
        private static Method f1621a;
        /* renamed from: b */
        private static boolean f1622b;

        C0720c() {
        }

        /* renamed from: a */
        public void mo590a(PopupWindow popupWindow, int i) {
            if (!f1622b) {
                try {
                    f1621a = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", new Class[]{Integer.TYPE});
                    f1621a.setAccessible(true);
                } catch (Exception e) {
                }
                f1622b = true;
            }
            if (f1621a != null) {
                try {
                    f1621a.invoke(popupWindow, new Object[]{Integer.valueOf(i)});
                } catch (Exception e2) {
                }
            }
        }

        /* renamed from: a */
        public void mo591a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            if ((C0625f.m3120a(i3, ah.m2812g(view)) & 7) == 5) {
                i -= popupWindow.getWidth() - view.getWidth();
            }
            popupWindow.showAsDropDown(view, i, i2);
        }

        /* renamed from: a */
        public void mo592a(PopupWindow popupWindow, boolean z) {
        }
    }

    /* renamed from: android.support.v4.widget.s$d */
    static class C0721d extends C0720c {
        C0721d() {
        }

        /* renamed from: a */
        public void mo591a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            C0727v.m3538a(popupWindow, view, i, i2, i3);
        }
    }

    /* renamed from: android.support.v4.widget.s$a */
    static class C0722a extends C0721d {
        C0722a() {
        }

        /* renamed from: a */
        public void mo592a(PopupWindow popupWindow, boolean z) {
            C0725t.m3535a(popupWindow, z);
        }
    }

    /* renamed from: android.support.v4.widget.s$b */
    static class C0723b extends C0722a {
        C0723b() {
        }

        /* renamed from: a */
        public void mo590a(PopupWindow popupWindow, int i) {
            C0726u.m3536a(popupWindow, i);
        }

        /* renamed from: a */
        public void mo592a(PopupWindow popupWindow, boolean z) {
            C0726u.m3537a(popupWindow, z);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            f1623a = new C0723b();
        } else if (i >= 21) {
            f1623a = new C0722a();
        } else if (i >= 19) {
            f1623a = new C0721d();
        } else {
            f1623a = new C0720c();
        }
    }

    /* renamed from: a */
    public static void m3532a(PopupWindow popupWindow, int i) {
        f1623a.mo590a(popupWindow, i);
    }

    /* renamed from: a */
    public static void m3533a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        f1623a.mo591a(popupWindow, view, i, i2, i3);
    }

    /* renamed from: a */
    public static void m3534a(PopupWindow popupWindow, boolean z) {
        f1623a.mo592a(popupWindow, z);
    }
}
