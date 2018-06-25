package android.support.v4.view;

import android.annotation.TargetApi;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

@TargetApi(14)
class av {
    /* renamed from: a */
    public static boolean m2968a(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        return viewParent.requestSendAccessibilityEvent(view, accessibilityEvent);
    }
}
