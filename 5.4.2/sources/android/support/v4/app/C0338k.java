package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;

@TargetApi(23)
/* renamed from: android.support.v4.app.k */
class C0338k {
    /* renamed from: a */
    public static int m1456a(Context context, String str, String str2) {
        return ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteProxyOp(str, str2);
    }

    /* renamed from: a */
    public static String m1457a(String str) {
        return AppOpsManager.permissionToOp(str);
    }
}
