package utils.view.VideoController;

import android.content.Context;

public class DensityUtil {
    /* renamed from: a */
    public static final float m14195a(Context context) {
        return (float) context.getResources().getDisplayMetrics().heightPixels;
    }

    /* renamed from: a */
    public static int m14196a(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    /* renamed from: b */
    public static final float m14197b(Context context) {
        return (float) context.getResources().getDisplayMetrics().widthPixels;
    }
}
