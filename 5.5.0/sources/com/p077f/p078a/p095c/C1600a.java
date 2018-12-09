package com.p077f.p078a.p095c;

import android.opengl.GLES10;
import com.p077f.p078a.p086b.p087a.C1553e;
import com.p077f.p078a.p086b.p087a.C1557h;
import com.p077f.p078a.p086b.p092e.C1580a;

/* renamed from: com.f.a.c.a */
public final class C1600a {
    /* renamed from: a */
    private static C1553e f4909a;

    static {
        int[] iArr = new int[1];
        GLES10.glGetIntegerv(3379, iArr, 0);
        int max = Math.max(iArr[0], 2048);
        f4909a = new C1553e(max, max);
    }

    /* renamed from: a */
    private static int m7925a(int i, int i2, int i3, boolean z) {
        int a = f4909a.m7673a();
        int b = f4909a.m7676b();
        while (true) {
            if (i / i3 <= a && i2 / i3 <= b) {
                return i3;
            }
            i3 = z ? i3 * 2 : i3 + 1;
        }
    }

    /* renamed from: a */
    public static int m7926a(C1553e c1553e) {
        int a = c1553e.m7673a();
        int b = c1553e.m7676b();
        return Math.max((int) Math.ceil((double) (((float) a) / ((float) f4909a.m7673a()))), (int) Math.ceil((double) (((float) b) / ((float) f4909a.m7676b()))));
    }

    /* renamed from: a */
    public static int m7927a(C1553e c1553e, C1553e c1553e2, C1557h c1557h, boolean z) {
        int max;
        int i = 1;
        int a = c1553e.m7673a();
        int b = c1553e.m7676b();
        int a2 = c1553e2.m7673a();
        int b2 = c1553e2.m7676b();
        int i2;
        int i3;
        switch (c1557h) {
            case FIT_INSIDE:
                if (!z) {
                    max = Math.max(a / a2, b / b2);
                    break;
                }
                i2 = a / 2;
                i3 = b / 2;
                max = 1;
                while (true) {
                    if (i2 / max <= a2 && i3 / max <= b2) {
                        break;
                    }
                    max *= 2;
                }
                break;
            case CROP:
                if (!z) {
                    max = Math.min(a / a2, b / b2);
                    break;
                }
                i2 = a / 2;
                i3 = b / 2;
                max = 1;
                while (i2 / max > a2 && i3 / max > b2) {
                    max *= 2;
                }
                break;
            default:
                max = 1;
                break;
        }
        if (max >= 1) {
            i = max;
        }
        return C1600a.m7925a(a, b, i, z);
    }

    /* renamed from: a */
    public static C1553e m7928a(C1580a c1580a, C1553e c1553e) {
        int a = c1580a.mo1228a();
        if (a <= 0) {
            a = c1553e.m7673a();
        }
        int b = c1580a.mo1231b();
        if (b <= 0) {
            b = c1553e.m7676b();
        }
        return new C1553e(a, b);
    }

    /* renamed from: b */
    public static float m7929b(C1553e c1553e, C1553e c1553e2, C1557h c1557h, boolean z) {
        int i;
        int a = c1553e.m7673a();
        int b = c1553e.m7676b();
        int a2 = c1553e2.m7673a();
        int b2 = c1553e2.m7676b();
        float f = ((float) a) / ((float) a2);
        float f2 = ((float) b) / ((float) b2);
        if ((c1557h != C1557h.FIT_INSIDE || f < f2) && (c1557h != C1557h.CROP || f >= f2)) {
            i = (int) (((float) a) / f2);
            a2 = b2;
        } else {
            i = a2;
            a2 = (int) (((float) b) / f);
        }
        return ((z || i >= a || a2 >= b) && (!z || i == a || a2 == b)) ? 1.0f : ((float) i) / ((float) a);
    }
}
