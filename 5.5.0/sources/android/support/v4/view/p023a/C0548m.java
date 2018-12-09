package android.support.v4.view.p023a;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

@TargetApi(16)
/* renamed from: android.support.v4.view.a.m */
class C0548m {

    /* renamed from: android.support.v4.view.a.m$a */
    interface C0539a {
        /* renamed from: a */
        Object mo415a(int i);

        /* renamed from: a */
        List<Object> mo416a(String str, int i);

        /* renamed from: a */
        boolean mo417a(int i, int i2, Bundle bundle);
    }

    /* renamed from: a */
    public static Object m2427a(final C0539a c0539a) {
        return new AccessibilityNodeProvider() {
            public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
                return (AccessibilityNodeInfo) c0539a.mo415a(i);
            }

            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str, int i) {
                return c0539a.mo416a(str, i);
            }

            public boolean performAction(int i, int i2, Bundle bundle) {
                return c0539a.mo417a(i, i2, bundle);
            }
        };
    }
}
