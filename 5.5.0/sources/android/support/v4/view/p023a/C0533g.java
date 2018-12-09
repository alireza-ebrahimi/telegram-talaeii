package android.support.v4.view.p023a;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

@TargetApi(14)
/* renamed from: android.support.v4.view.a.g */
class C0533g {
    /* renamed from: a */
    public static Object m2354a() {
        return AccessibilityNodeInfo.obtain();
    }

    /* renamed from: a */
    public static Object m2355a(View view) {
        return AccessibilityNodeInfo.obtain(view);
    }

    /* renamed from: a */
    public static Object m2356a(Object obj) {
        return AccessibilityNodeInfo.obtain((AccessibilityNodeInfo) obj);
    }

    /* renamed from: a */
    public static void m2357a(Object obj, int i) {
        ((AccessibilityNodeInfo) obj).addAction(i);
    }

    /* renamed from: a */
    public static void m2358a(Object obj, Rect rect) {
        ((AccessibilityNodeInfo) obj).getBoundsInParent(rect);
    }

    /* renamed from: a */
    public static void m2359a(Object obj, View view) {
        ((AccessibilityNodeInfo) obj).setParent(view);
    }

    /* renamed from: a */
    public static void m2360a(Object obj, CharSequence charSequence) {
        ((AccessibilityNodeInfo) obj).setClassName(charSequence);
    }

    /* renamed from: a */
    public static void m2361a(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setCheckable(z);
    }

    /* renamed from: b */
    public static int m2362b(Object obj) {
        return ((AccessibilityNodeInfo) obj).getActions();
    }

    /* renamed from: b */
    public static void m2363b(Object obj, Rect rect) {
        ((AccessibilityNodeInfo) obj).getBoundsInScreen(rect);
    }

    /* renamed from: b */
    public static void m2364b(Object obj, CharSequence charSequence) {
        ((AccessibilityNodeInfo) obj).setContentDescription(charSequence);
    }

    /* renamed from: b */
    public static void m2365b(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setChecked(z);
    }

    /* renamed from: c */
    public static int m2366c(Object obj) {
        return ((AccessibilityNodeInfo) obj).getChildCount();
    }

    /* renamed from: c */
    public static void m2367c(Object obj, Rect rect) {
        ((AccessibilityNodeInfo) obj).setBoundsInParent(rect);
    }

    /* renamed from: c */
    public static void m2368c(Object obj, CharSequence charSequence) {
        ((AccessibilityNodeInfo) obj).setPackageName(charSequence);
    }

    /* renamed from: c */
    public static void m2369c(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setEnabled(z);
    }

    /* renamed from: d */
    public static CharSequence m2370d(Object obj) {
        return ((AccessibilityNodeInfo) obj).getClassName();
    }

    /* renamed from: d */
    public static void m2371d(Object obj, Rect rect) {
        ((AccessibilityNodeInfo) obj).setBoundsInScreen(rect);
    }

    /* renamed from: d */
    public static void m2372d(Object obj, CharSequence charSequence) {
        ((AccessibilityNodeInfo) obj).setText(charSequence);
    }

    /* renamed from: d */
    public static void m2373d(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setFocusable(z);
    }

    /* renamed from: e */
    public static CharSequence m2374e(Object obj) {
        return ((AccessibilityNodeInfo) obj).getContentDescription();
    }

    /* renamed from: e */
    public static void m2375e(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setFocused(z);
    }

    /* renamed from: f */
    public static CharSequence m2376f(Object obj) {
        return ((AccessibilityNodeInfo) obj).getPackageName();
    }

    /* renamed from: f */
    public static void m2377f(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setScrollable(z);
    }

    /* renamed from: g */
    public static CharSequence m2378g(Object obj) {
        return ((AccessibilityNodeInfo) obj).getText();
    }

    /* renamed from: g */
    public static void m2379g(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setSelected(z);
    }

    /* renamed from: h */
    public static boolean m2380h(Object obj) {
        return ((AccessibilityNodeInfo) obj).isCheckable();
    }

    /* renamed from: i */
    public static boolean m2381i(Object obj) {
        return ((AccessibilityNodeInfo) obj).isChecked();
    }

    /* renamed from: j */
    public static boolean m2382j(Object obj) {
        return ((AccessibilityNodeInfo) obj).isClickable();
    }

    /* renamed from: k */
    public static boolean m2383k(Object obj) {
        return ((AccessibilityNodeInfo) obj).isEnabled();
    }

    /* renamed from: l */
    public static boolean m2384l(Object obj) {
        return ((AccessibilityNodeInfo) obj).isFocusable();
    }

    /* renamed from: m */
    public static boolean m2385m(Object obj) {
        return ((AccessibilityNodeInfo) obj).isFocused();
    }

    /* renamed from: n */
    public static boolean m2386n(Object obj) {
        return ((AccessibilityNodeInfo) obj).isLongClickable();
    }

    /* renamed from: o */
    public static boolean m2387o(Object obj) {
        return ((AccessibilityNodeInfo) obj).isPassword();
    }

    /* renamed from: p */
    public static boolean m2388p(Object obj) {
        return ((AccessibilityNodeInfo) obj).isScrollable();
    }

    /* renamed from: q */
    public static boolean m2389q(Object obj) {
        return ((AccessibilityNodeInfo) obj).isSelected();
    }

    /* renamed from: r */
    public static void m2390r(Object obj) {
        ((AccessibilityNodeInfo) obj).recycle();
    }
}
