package utils.view.bottombar;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import org.ir.talaeii.R;

final class NavbarUtils {
    NavbarUtils() {
    }

    /* renamed from: a */
    static int m14349a(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return identifier > 0 ? resources.getDimensionPixelSize(identifier) : 0;
    }

    /* renamed from: b */
    static boolean m14350b(Context context) {
        return m14351c(context) && m14352d(context);
    }

    /* renamed from: c */
    private static boolean m14351c(Context context) {
        return context.getResources().getBoolean(R.bool.bb_bottom_bar_is_portrait_mode);
    }

    /* renamed from: d */
    private static boolean m14352d(Context context) {
        if (VERSION.SDK_INT >= 17) {
            Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getRealMetrics(displayMetrics);
            int i = displayMetrics.heightPixels;
            int i2 = displayMetrics.widthPixels;
            DisplayMetrics displayMetrics2 = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics2);
            boolean z = i2 - displayMetrics2.widthPixels > 0 || i - displayMetrics2.heightPixels > 0;
            return z;
        } else if (VERSION.SDK_INT < 14) {
            return true;
        } else {
            return (ViewConfiguration.get(context).hasPermanentMenuKey() || KeyCharacterMap.deviceHasKey(4)) ? false : true;
        }
    }
}
