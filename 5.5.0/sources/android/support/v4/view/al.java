package android.support.v4.view;

import android.annotation.TargetApi;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(14)
class al {
    /* renamed from: a */
    public static void m2879a(View view, AccessibilityEvent accessibilityEvent) {
        view.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    /* renamed from: a */
    public static void m2880a(View view, Object obj) {
        view.setAccessibilityDelegate((AccessibilityDelegate) obj);
    }

    /* renamed from: a */
    public static void m2881a(View view, boolean z) {
        view.setFitsSystemWindows(z);
    }

    /* renamed from: a */
    public static boolean m2882a(View view, int i) {
        return view.canScrollHorizontally(i);
    }

    /* renamed from: b */
    public static void m2883b(View view, Object obj) {
        view.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo) obj);
    }

    /* renamed from: b */
    public static boolean m2884b(View view, int i) {
        return view.canScrollVertically(i);
    }
}
