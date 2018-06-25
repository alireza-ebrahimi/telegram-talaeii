package utils.view.VideoController;

import android.content.Context;

public class DensityUtil {
    public static final float getHeightInPx(Context context) {
        return (float) context.getResources().getDisplayMetrics().heightPixels;
    }

    public static final float getWidthInPx(Context context) {
        return (float) context.getResources().getDisplayMetrics().widthPixels;
    }

    public static final int getHeightInDp(Context context) {
        return px2dip(context, (float) context.getResources().getDisplayMetrics().heightPixels);
    }

    public static final int getWidthInDp(Context context) {
        return px2dip(context, (float) context.getResources().getDisplayMetrics().heightPixels);
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) ((spValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
