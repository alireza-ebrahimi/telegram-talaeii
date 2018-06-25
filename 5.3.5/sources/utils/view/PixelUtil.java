package utils.view;

import android.content.Context;
import android.util.DisplayMetrics;

public class PixelUtil {
    private PixelUtil() {
    }

    public static int dpToPx(Context context, int dp) {
        return Math.round(((float) dp) * getPixelScaleFactor(context));
    }

    public static int pxToDp(Context context, int px) {
        return Math.round(((float) px) / getPixelScaleFactor(context));
    }

    private static float getPixelScaleFactor(Context context) {
        return context.getResources().getDisplayMetrics().xdpi / 160.0f;
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
    }
}
