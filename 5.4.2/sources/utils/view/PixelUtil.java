package utils.view;

import android.content.Context;
import android.util.DisplayMetrics;

public class PixelUtil {
    private PixelUtil() {
    }

    /* renamed from: a */
    public static int m14172a(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
    }

    /* renamed from: a */
    public static int m14173a(Context context, int i) {
        return Math.round(((float) i) * m14174b(context));
    }

    /* renamed from: b */
    private static float m14174b(Context context) {
        return context.getResources().getDisplayMetrics().xdpi / 160.0f;
    }

    /* renamed from: b */
    public static int m14175b(Context context, int i) {
        return Math.round(((float) i) / m14174b(context));
    }
}
