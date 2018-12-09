package android.support.v4.view.p023a;

import android.annotation.TargetApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo;

@TargetApi(21)
/* renamed from: android.support.v4.view.a.f */
class C0532f {
    /* renamed from: a */
    public static Object m2351a(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        return CollectionItemInfo.obtain(i, i2, i3, i4, z, z2);
    }

    /* renamed from: a */
    public static Object m2352a(int i, int i2, boolean z, int i3) {
        return CollectionInfo.obtain(i, i2, z, i3);
    }

    /* renamed from: a */
    public static void m2353a(Object obj, CharSequence charSequence) {
        ((AccessibilityNodeInfo) obj).setError(charSequence);
    }
}
