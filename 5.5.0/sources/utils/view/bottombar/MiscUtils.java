package utils.view.bottombar;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

class MiscUtils {
    MiscUtils() {
    }

    /* renamed from: a */
    protected static int m14345a(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (((float) displayMetrics.widthPixels) / displayMetrics.density);
    }

    /* renamed from: a */
    protected static int m14346a(Context context, float f) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        try {
            return (int) ((((float) displayMetrics.densityDpi) / 160.0f) * f);
        } catch (NoSuchFieldError e) {
            return (int) TypedValue.applyDimension(1, f, displayMetrics);
        }
    }

    /* renamed from: a */
    protected static int m14347a(Context context, int i) {
        return Math.round(((float) i) / (context.getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    /* renamed from: a */
    protected static void m14348a(TextView textView, int i) {
        if (VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(i);
        } else {
            textView.setTextAppearance(textView.getContext(), i);
        }
    }
}
