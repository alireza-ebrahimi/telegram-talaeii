package android.support.v4.view;

import android.annotation.TargetApi;
import android.view.KeyEvent;

@TargetApi(11)
/* renamed from: android.support.v4.view.i */
class C0631i {
    /* renamed from: a */
    public static int m3139a(int i) {
        return KeyEvent.normalizeMetaState(i);
    }

    /* renamed from: a */
    public static boolean m3140a(int i, int i2) {
        return KeyEvent.metaStateHasModifiers(i, i2);
    }

    /* renamed from: a */
    public static boolean m3141a(KeyEvent keyEvent) {
        return keyEvent.isCtrlPressed();
    }

    /* renamed from: b */
    public static boolean m3142b(int i) {
        return KeyEvent.metaStateHasNoModifiers(i);
    }
}
