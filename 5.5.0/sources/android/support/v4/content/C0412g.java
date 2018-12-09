package android.support.v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import java.io.File;

@TargetApi(19)
/* renamed from: android.support.v4.content.g */
class C0412g {
    /* renamed from: a */
    public static File[] m1879a(Context context) {
        return context.getExternalCacheDirs();
    }

    /* renamed from: a */
    public static File[] m1880a(Context context, String str) {
        return context.getExternalFilesDirs(str);
    }
}
