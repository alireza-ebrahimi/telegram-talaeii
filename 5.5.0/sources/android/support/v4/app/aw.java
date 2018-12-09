package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.lang.reflect.InvocationTargetException;

@TargetApi(19)
class aw {
    /* renamed from: a */
    public static boolean m1388a(Context context) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String packageName = context.getApplicationContext().getPackageName();
        int i = applicationInfo.uid;
        try {
            return ((Integer) Class.forName(AppOpsManager.class.getName()).getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) Class.forName(AppOpsManager.class.getName()).getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), packageName})).intValue() == 0;
        } catch (ClassNotFoundException e) {
            return true;
        } catch (NoSuchMethodException e2) {
            return true;
        } catch (NoSuchFieldException e3) {
            return true;
        } catch (InvocationTargetException e4) {
            return true;
        } catch (IllegalAccessException e5) {
            return true;
        } catch (RuntimeException e6) {
            return true;
        }
    }
}
