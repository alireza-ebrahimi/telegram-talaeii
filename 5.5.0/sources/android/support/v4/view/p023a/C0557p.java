package android.support.v4.view.p023a;

import android.annotation.TargetApi;
import android.view.accessibility.AccessibilityRecord;
import java.util.List;

@TargetApi(14)
/* renamed from: android.support.v4.view.a.p */
class C0557p {
    /* renamed from: a */
    public static List<CharSequence> m2489a(Object obj) {
        return ((AccessibilityRecord) obj).getText();
    }

    /* renamed from: a */
    public static void m2490a(Object obj, int i) {
        ((AccessibilityRecord) obj).setFromIndex(i);
    }

    /* renamed from: a */
    public static void m2491a(Object obj, CharSequence charSequence) {
        ((AccessibilityRecord) obj).setClassName(charSequence);
    }

    /* renamed from: a */
    public static void m2492a(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setChecked(z);
    }

    /* renamed from: b */
    public static void m2493b(Object obj, int i) {
        ((AccessibilityRecord) obj).setItemCount(i);
    }

    /* renamed from: b */
    public static void m2494b(Object obj, CharSequence charSequence) {
        ((AccessibilityRecord) obj).setContentDescription(charSequence);
    }

    /* renamed from: b */
    public static void m2495b(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setEnabled(z);
    }

    /* renamed from: c */
    public static void m2496c(Object obj, int i) {
        ((AccessibilityRecord) obj).setScrollX(i);
    }

    /* renamed from: c */
    public static void m2497c(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setPassword(z);
    }

    /* renamed from: d */
    public static void m2498d(Object obj, int i) {
        ((AccessibilityRecord) obj).setScrollY(i);
    }

    /* renamed from: d */
    public static void m2499d(Object obj, boolean z) {
        ((AccessibilityRecord) obj).setScrollable(z);
    }

    /* renamed from: e */
    public static void m2500e(Object obj, int i) {
        ((AccessibilityRecord) obj).setToIndex(i);
    }
}
