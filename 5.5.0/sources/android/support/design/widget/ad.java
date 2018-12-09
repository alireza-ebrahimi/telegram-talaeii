package android.support.design.widget;

import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.support.design.widget.C0201w.C0127d;

class ad {
    /* renamed from: a */
    static final C0127d f438a = new C01281();

    /* renamed from: android.support.design.widget.ad$1 */
    static class C01281 implements C0127d {
        C01281() {
        }

        /* renamed from: a */
        public C0201w mo123a() {
            return new C0201w(VERSION.SDK_INT >= 12 ? new C0206y() : new C0203x());
        }
    }

    /* renamed from: a */
    static Mode m658a(int i, Mode mode) {
        switch (i) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            default:
                return mode;
        }
    }

    /* renamed from: a */
    static C0201w m659a() {
        return f438a.mo123a();
    }

    /* renamed from: a */
    static boolean m660a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }
}
