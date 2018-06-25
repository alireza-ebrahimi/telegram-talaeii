package org.telegram.customization.util;

import android.content.Context;
import android.util.TypedValue;

public class Size {
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, context.getResources().getDisplayMetrics());
    }
}
