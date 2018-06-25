package utils.view.collectionpicker;

import android.content.Context;

public class Utils {
    public static int dpToPx(Context context, int dp) {
        return Math.round(((float) dp) * context.getResources().getDisplayMetrics().density);
    }
}
