package android.support.v4.view;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

@TargetApi(16)
/* renamed from: android.support.v4.view.c */
class C0613c {

    /* renamed from: android.support.v4.view.c$a */
    public interface C0502a {
        /* renamed from: a */
        Object mo352a(View view);

        /* renamed from: a */
        void mo353a(View view, int i);

        /* renamed from: a */
        void mo354a(View view, Object obj);

        /* renamed from: a */
        boolean mo355a(View view, int i, Bundle bundle);

        /* renamed from: a */
        boolean mo356a(View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: a */
        boolean mo357a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: b */
        void mo358b(View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: c */
        void mo359c(View view, AccessibilityEvent accessibilityEvent);

        /* renamed from: d */
        void mo360d(View view, AccessibilityEvent accessibilityEvent);
    }

    /* renamed from: a */
    public static Object m3088a(final C0502a c0502a) {
        return new AccessibilityDelegate() {
            public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                return c0502a.mo356a(view, accessibilityEvent);
            }

            public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
                return (AccessibilityNodeProvider) c0502a.mo352a(view);
            }

            public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                c0502a.mo358b(view, accessibilityEvent);
            }

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                c0502a.mo354a(view, (Object) accessibilityNodeInfo);
            }

            public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                c0502a.mo359c(view, accessibilityEvent);
            }

            public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                return c0502a.mo357a(viewGroup, view, accessibilityEvent);
            }

            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                return c0502a.mo355a(view, i, bundle);
            }

            public void sendAccessibilityEvent(View view, int i) {
                c0502a.mo353a(view, i);
            }

            public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
                c0502a.mo360d(view, accessibilityEvent);
            }
        };
    }

    /* renamed from: a */
    public static Object m3089a(Object obj, View view) {
        return ((AccessibilityDelegate) obj).getAccessibilityNodeProvider(view);
    }

    /* renamed from: a */
    public static boolean m3090a(Object obj, View view, int i, Bundle bundle) {
        return ((AccessibilityDelegate) obj).performAccessibilityAction(view, i, bundle);
    }
}
