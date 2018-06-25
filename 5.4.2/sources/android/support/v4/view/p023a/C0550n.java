package android.support.v4.view.p023a;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

@TargetApi(19)
/* renamed from: android.support.v4.view.a.n */
class C0550n {

    /* renamed from: android.support.v4.view.a.n$a */
    interface C0543a {
        /* renamed from: a */
        Object mo419a(int i);

        /* renamed from: a */
        List<Object> mo420a(String str, int i);

        /* renamed from: a */
        boolean mo421a(int i, int i2, Bundle bundle);

        /* renamed from: b */
        Object mo422b(int i);
    }

    /* renamed from: a */
    public static Object m2428a(final C0543a c0543a) {
        return new AccessibilityNodeProvider() {
            public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
                return (AccessibilityNodeInfo) c0543a.mo419a(i);
            }

            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str, int i) {
                return c0543a.mo420a(str, i);
            }

            public AccessibilityNodeInfo findFocus(int i) {
                return (AccessibilityNodeInfo) c0543a.mo422b(i);
            }

            public boolean performAction(int i, int i2, Bundle bundle) {
                return c0543a.mo421a(i, i2, bundle);
            }
        };
    }
}
