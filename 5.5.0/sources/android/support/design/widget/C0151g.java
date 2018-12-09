package android.support.design.widget;

import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.util.Log;
import java.lang.reflect.Method;

/* renamed from: android.support.design.widget.g */
class C0151g {
    /* renamed from: a */
    private static Method f522a;
    /* renamed from: b */
    private static boolean f523b;

    /* renamed from: a */
    static boolean m744a(DrawableContainer drawableContainer, ConstantState constantState) {
        return C0151g.m745b(drawableContainer, constantState);
    }

    /* renamed from: b */
    private static boolean m745b(DrawableContainer drawableContainer, ConstantState constantState) {
        if (!f523b) {
            try {
                f522a = DrawableContainer.class.getDeclaredMethod("setConstantState", new Class[]{DrawableContainerState.class});
                f522a.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.e("DrawableUtils", "Could not fetch setConstantState(). Oh well.");
            }
            f523b = true;
        }
        if (f522a != null) {
            try {
                f522a.invoke(drawableContainer, new Object[]{constantState});
                return true;
            } catch (Exception e2) {
                Log.e("DrawableUtils", "Could not invoke setConstantState(). Oh well.");
            }
        }
        return false;
    }
}
