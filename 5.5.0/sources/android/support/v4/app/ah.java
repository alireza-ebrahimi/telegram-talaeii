package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

@TargetApi(16)
class ah {
    /* renamed from: a */
    public static Intent m1194a(Activity activity) {
        return activity.getParentActivityIntent();
    }

    /* renamed from: a */
    public static String m1195a(ActivityInfo activityInfo) {
        return activityInfo.parentActivityName;
    }

    /* renamed from: a */
    public static boolean m1196a(Activity activity, Intent intent) {
        return activity.shouldUpRecreateTask(intent);
    }

    /* renamed from: b */
    public static void m1197b(Activity activity, Intent intent) {
        activity.navigateUpTo(intent);
    }
}
