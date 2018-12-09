package org.telegram.customization.util.view.p171b;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

/* renamed from: org.telegram.customization.util.view.b.b */
public class C2928b {
    /* renamed from: a */
    public static int m13549a(Context context, int i) {
        return (int) TypedValue.applyDimension(1, (float) i, context.getResources().getDisplayMetrics());
    }

    /* renamed from: a */
    public static boolean m13550a(View view, int i, int i2) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i3 = iArr[0];
        int i4 = iArr[1];
        return i >= i3 && i <= i3 + view.getWidth() && i2 >= i4 && i2 <= i4 + view.getHeight();
    }
}
