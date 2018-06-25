package android.support.v4.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

@TargetApi(9)
/* renamed from: android.support.v4.widget.e */
class C0689e {
    /* renamed from: a */
    private static Field f1547a;
    /* renamed from: b */
    private static boolean f1548b;

    /* renamed from: a */
    static Drawable m3372a(CompoundButton compoundButton) {
        if (!f1548b) {
            try {
                f1547a = CompoundButton.class.getDeclaredField("mButtonDrawable");
                f1547a.setAccessible(true);
            } catch (Throwable e) {
                Log.i("CompoundButtonCompatGingerbread", "Failed to retrieve mButtonDrawable field", e);
            }
            f1548b = true;
        }
        if (f1547a != null) {
            try {
                return (Drawable) f1547a.get(compoundButton);
            } catch (Throwable e2) {
                Log.i("CompoundButtonCompatGingerbread", "Failed to get button drawable via reflection", e2);
                f1547a = null;
            }
        }
        return null;
    }

    /* renamed from: a */
    static void m3373a(CompoundButton compoundButton, ColorStateList colorStateList) {
        if (compoundButton instanceof af) {
            ((af) compoundButton).setSupportButtonTintList(colorStateList);
        }
    }

    /* renamed from: a */
    static void m3374a(CompoundButton compoundButton, Mode mode) {
        if (compoundButton instanceof af) {
            ((af) compoundButton).setSupportButtonTintMode(mode);
        }
    }
}
