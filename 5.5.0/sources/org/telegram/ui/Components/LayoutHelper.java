package org.telegram.ui.Components;

import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;

public class LayoutHelper {
    public static final int MATCH_PARENT = -1;
    public static final int WRAP_CONTENT = -2;

    public static LayoutParams createFrame(int i, float f) {
        return new LayoutParams(getSize((float) i), getSize(f));
    }

    public static LayoutParams createFrame(int i, float f, int i2, float f2, float f3, float f4, float f5) {
        LayoutParams layoutParams = new LayoutParams(getSize((float) i), getSize(f), i2);
        layoutParams.setMargins(AndroidUtilities.dp(f2), AndroidUtilities.dp(f3), AndroidUtilities.dp(f4), AndroidUtilities.dp(f5));
        return layoutParams;
    }

    public static LayoutParams createFrame(int i, int i2, int i3) {
        return new LayoutParams(getSize((float) i), getSize((float) i2), i3);
    }

    public static LinearLayout.LayoutParams createLinear(int i, int i2) {
        return new LinearLayout.LayoutParams(getSize((float) i), getSize((float) i2));
    }

    public static LinearLayout.LayoutParams createLinear(int i, int i2, float f) {
        return new LinearLayout.LayoutParams(getSize((float) i), getSize((float) i2), f);
    }

    public static LinearLayout.LayoutParams createLinear(int i, int i2, float f, float f2, float f3, float f4) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize((float) i), getSize((float) i2));
        layoutParams.setMargins(AndroidUtilities.dp(f), AndroidUtilities.dp(f2), AndroidUtilities.dp(f3), AndroidUtilities.dp(f4));
        return layoutParams;
    }

    public static LinearLayout.LayoutParams createLinear(int i, int i2, float f, int i3) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize((float) i), getSize((float) i2), f);
        layoutParams.gravity = i3;
        return layoutParams;
    }

    public static LinearLayout.LayoutParams createLinear(int i, int i2, float f, int i3, int i4, int i5, int i6) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize((float) i), getSize((float) i2), f);
        layoutParams.setMargins(AndroidUtilities.dp((float) i3), AndroidUtilities.dp((float) i4), AndroidUtilities.dp((float) i5), AndroidUtilities.dp((float) i6));
        return layoutParams;
    }

    public static LinearLayout.LayoutParams createLinear(int i, int i2, float f, int i3, int i4, int i5, int i6, int i7) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize((float) i), getSize((float) i2), f);
        layoutParams.setMargins(AndroidUtilities.dp((float) i4), AndroidUtilities.dp((float) i5), AndroidUtilities.dp((float) i6), AndroidUtilities.dp((float) i7));
        layoutParams.gravity = i3;
        return layoutParams;
    }

    public static LinearLayout.LayoutParams createLinear(int i, int i2, int i3) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize((float) i), getSize((float) i2));
        layoutParams.gravity = i3;
        return layoutParams;
    }

    public static LinearLayout.LayoutParams createLinear(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize((float) i), getSize((float) i2));
        layoutParams.setMargins(AndroidUtilities.dp((float) i4), AndroidUtilities.dp((float) i5), AndroidUtilities.dp((float) i6), AndroidUtilities.dp((float) i7));
        layoutParams.gravity = i3;
        return layoutParams;
    }

    public static RelativeLayout.LayoutParams createRelative(float f, float f2, int i, int i2, int i3, int i4, int i5, int i6) {
        return createRelative(f, f2, i, i2, i3, i4, -1, i5, i6);
    }

    public static RelativeLayout.LayoutParams createRelative(float f, float f2, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getSize(f), getSize(f2));
        if (i5 >= 0) {
            layoutParams.addRule(i5);
        }
        if (i6 >= 0 && i7 >= 0) {
            layoutParams.addRule(i6, i7);
        }
        layoutParams.leftMargin = AndroidUtilities.dp((float) i);
        layoutParams.topMargin = AndroidUtilities.dp((float) i2);
        layoutParams.rightMargin = AndroidUtilities.dp((float) i3);
        layoutParams.bottomMargin = AndroidUtilities.dp((float) i4);
        return layoutParams;
    }

    public static RelativeLayout.LayoutParams createRelative(int i, int i2) {
        return createRelative((float) i, (float) i2, 0, 0, 0, 0, -1, -1, -1);
    }

    public static RelativeLayout.LayoutParams createRelative(int i, int i2, int i3) {
        return createRelative((float) i, (float) i2, 0, 0, 0, 0, i3, -1, -1);
    }

    public static RelativeLayout.LayoutParams createRelative(int i, int i2, int i3, int i4) {
        return createRelative((float) i, (float) i2, 0, 0, 0, 0, -1, i3, i4);
    }

    public static RelativeLayout.LayoutParams createRelative(int i, int i2, int i3, int i4, int i5) {
        return createRelative((float) i, (float) i2, 0, 0, 0, 0, i3, i4, i5);
    }

    public static RelativeLayout.LayoutParams createRelative(int i, int i2, int i3, int i4, int i5, int i6) {
        return createRelative((float) i, (float) i2, i3, i4, i5, i6, -1, -1, -1);
    }

    public static RelativeLayout.LayoutParams createRelative(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return createRelative((float) i, (float) i2, i3, i4, i5, i6, i7, -1, -1);
    }

    public static LayoutParams createScroll(int i, int i2, int i3) {
        return new LayoutParams(getSize((float) i), getSize((float) i2), i3);
    }

    private static int getSize(float f) {
        if (f >= BitmapDescriptorFactory.HUE_RED) {
            f = (float) AndroidUtilities.dp(f);
        }
        return (int) f;
    }
}
