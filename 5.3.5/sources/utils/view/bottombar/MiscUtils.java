package utils.view.bottombar;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

class MiscUtils {
    MiscUtils() {
    }

    protected static int getColor(Context context, int color) {
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(color, tv, true);
        return tv.data;
    }

    protected static int dpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        try {
            return (int) ((((float) metrics.densityDpi) / 160.0f) * dp);
        } catch (NoSuchFieldError e) {
            return (int) TypedValue.applyDimension(1, dp, metrics);
        }
    }

    protected static int pixelToDp(Context context, int px) {
        return Math.round(((float) px) / (context.getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    protected static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
    }

    protected static void setTextAppearance(TextView textView, int resId) {
        if (VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(resId);
        } else {
            textView.setTextAppearance(textView.getContext(), resId);
        }
    }

    protected static boolean isNightMode(Context context) {
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }
}
