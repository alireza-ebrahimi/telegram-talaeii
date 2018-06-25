package android.support.v4.widget;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;
import java.lang.reflect.Field;

@TargetApi(9)
class ab {
    /* renamed from: a */
    private static Field f1513a;
    /* renamed from: b */
    private static boolean f1514b;
    /* renamed from: c */
    private static Field f1515c;
    /* renamed from: d */
    private static boolean f1516d;

    /* renamed from: a */
    static int m3311a(TextView textView) {
        if (!f1516d) {
            f1515c = m3313a("mMaxMode");
            f1516d = true;
        }
        if (f1515c != null && m3312a(f1515c, textView) == 1) {
            if (!f1514b) {
                f1513a = m3313a("mMaximum");
                f1514b = true;
            }
            if (f1513a != null) {
                return m3312a(f1513a, textView);
            }
        }
        return -1;
    }

    /* renamed from: a */
    private static int m3312a(Field field, TextView textView) {
        try {
            return field.getInt(textView);
        } catch (IllegalAccessException e) {
            Log.d("TextViewCompatGingerbread", "Could not retrieve value of " + field.getName() + " field.");
            return -1;
        }
    }

    /* renamed from: a */
    private static Field m3313a(String str) {
        Field field = null;
        try {
            field = TextView.class.getDeclaredField(str);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            Log.e("TextViewCompatGingerbread", "Could not retrieve " + str + " field.");
            return field;
        }
    }

    /* renamed from: a */
    static void m3314a(TextView textView, int i) {
        textView.setTextAppearance(textView.getContext(), i);
    }

    /* renamed from: b */
    static Drawable[] m3315b(TextView textView) {
        return textView.getCompoundDrawables();
    }
}
