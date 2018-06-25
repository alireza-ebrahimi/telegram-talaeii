package android.support.v4.view.p023a;

import android.annotation.TargetApi;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo;
import android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo;

@TargetApi(19)
/* renamed from: android.support.v4.view.a.k */
class C0537k {
    /* renamed from: a */
    public static Object m2398a(int i, int i2, int i3, int i4, boolean z) {
        return CollectionItemInfo.obtain(i, i2, i3, i4, z);
    }

    /* renamed from: a */
    public static Object m2399a(int i, int i2, boolean z, int i3) {
        return CollectionInfo.obtain(i, i2, z);
    }

    /* renamed from: a */
    public static void m2400a(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).setCollectionInfo((CollectionInfo) obj2);
    }

    /* renamed from: a */
    public static void m2401a(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setContentInvalid(z);
    }

    /* renamed from: b */
    public static void m2402b(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).setCollectionItemInfo((CollectionItemInfo) obj2);
    }

    /* renamed from: b */
    public static void m2403b(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setDismissable(z);
    }
}
