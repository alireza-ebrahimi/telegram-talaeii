package android.support.v4.widget;

import android.annotation.TargetApi;
import android.util.Log;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

@TargetApi(21)
/* renamed from: android.support.v4.widget.t */
class C0725t {
    /* renamed from: a */
    private static Field f1624a;

    static {
        try {
            f1624a = PopupWindow.class.getDeclaredField("mOverlapAnchor");
            f1624a.setAccessible(true);
        } catch (Throwable e) {
            Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", e);
        }
    }

    /* renamed from: a */
    static void m3535a(PopupWindow popupWindow, boolean z) {
        if (f1624a != null) {
            try {
                f1624a.set(popupWindow, Boolean.valueOf(z));
            } catch (Throwable e) {
                Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", e);
            }
        }
    }
}
