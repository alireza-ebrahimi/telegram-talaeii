package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.p025a.C0748a.C0738a;

/* renamed from: android.support.design.widget.v */
class C0195v {
    /* renamed from: a */
    private static final int[] f677a = new int[]{C0738a.colorPrimary};

    /* renamed from: a */
    static void m916a(Context context) {
        int i = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(f677a);
        if (!obtainStyledAttributes.hasValue(0)) {
            i = 1;
        }
        obtainStyledAttributes.recycle();
        if (i != 0) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
        }
    }
}
