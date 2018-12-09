package android.support.v4.view.p023a;

import android.annotation.TargetApi;
import android.view.accessibility.AccessibilityEvent;

@TargetApi(19)
/* renamed from: android.support.v4.view.a.b */
class C0511b {
    /* renamed from: a */
    public static int m2135a(AccessibilityEvent accessibilityEvent) {
        return accessibilityEvent.getContentChangeTypes();
    }

    /* renamed from: a */
    public static void m2136a(AccessibilityEvent accessibilityEvent, int i) {
        accessibilityEvent.setContentChangeTypes(i);
    }
}
