package android.support.v4.view.p023a;

import android.annotation.TargetApi;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(16)
/* renamed from: android.support.v4.view.a.h */
class C0534h {
    /* renamed from: a */
    public static void m2391a(Object obj, View view, int i) {
        ((AccessibilityNodeInfo) obj).addChild(view, i);
    }

    /* renamed from: a */
    public static void m2392a(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setVisibleToUser(z);
    }

    /* renamed from: b */
    public static void m2393b(Object obj, View view, int i) {
        ((AccessibilityNodeInfo) obj).setSource(view, i);
    }

    /* renamed from: b */
    public static void m2394b(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setAccessibilityFocused(z);
    }

    /* renamed from: c */
    public static void m2395c(Object obj, View view, int i) {
        ((AccessibilityNodeInfo) obj).setParent(view, i);
    }
}
