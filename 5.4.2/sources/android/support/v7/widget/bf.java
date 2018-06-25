package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.p007b.C0392a;
import android.util.TypedValue;

class bf {
    /* renamed from: a */
    static final int[] f3025a = new int[]{-16842910};
    /* renamed from: b */
    static final int[] f3026b = new int[]{16842908};
    /* renamed from: c */
    static final int[] f3027c = new int[]{16843518};
    /* renamed from: d */
    static final int[] f3028d = new int[]{16842919};
    /* renamed from: e */
    static final int[] f3029e = new int[]{16842912};
    /* renamed from: f */
    static final int[] f3030f = new int[]{16842913};
    /* renamed from: g */
    static final int[] f3031g = new int[]{-16842919, -16842908};
    /* renamed from: h */
    static final int[] f3032h = new int[0];
    /* renamed from: i */
    private static final ThreadLocal<TypedValue> f3033i = new ThreadLocal();
    /* renamed from: j */
    private static final int[] f3034j = new int[1];

    /* renamed from: a */
    public static int m5642a(Context context, int i) {
        f3034j[0] = i;
        bk a = bk.m5653a(context, null, f3034j);
        try {
            int b = a.m5660b(0, 0);
            return b;
        } finally {
            a.m5658a();
        }
    }

    /* renamed from: a */
    static int m5643a(Context context, int i, float f) {
        int a = m5642a(context, i);
        return C0392a.m1834c(a, Math.round(((float) Color.alpha(a)) * f));
    }

    /* renamed from: a */
    private static TypedValue m5644a() {
        TypedValue typedValue = (TypedValue) f3033i.get();
        if (typedValue != null) {
            return typedValue;
        }
        typedValue = new TypedValue();
        f3033i.set(typedValue);
        return typedValue;
    }

    /* renamed from: b */
    public static ColorStateList m5645b(Context context, int i) {
        f3034j[0] = i;
        bk a = bk.m5653a(context, null, f3034j);
        try {
            ColorStateList e = a.m5667e(0);
            return e;
        } finally {
            a.m5658a();
        }
    }

    /* renamed from: c */
    public static int m5646c(Context context, int i) {
        ColorStateList b = m5645b(context, i);
        if (b != null && b.isStateful()) {
            return b.getColorForState(f3025a, b.getDefaultColor());
        }
        TypedValue a = m5644a();
        context.getTheme().resolveAttribute(16842803, a, true);
        return m5643a(context, i, a.getFloat());
    }
}
