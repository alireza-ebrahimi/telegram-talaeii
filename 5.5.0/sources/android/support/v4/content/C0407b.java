package android.support.v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.File;

@TargetApi(21)
/* renamed from: android.support.v4.content.b */
class C0407b {
    /* renamed from: a */
    public static Drawable m1871a(Context context, int i) {
        return context.getDrawable(i);
    }

    /* renamed from: a */
    public static File m1872a(Context context) {
        return context.getNoBackupFilesDir();
    }
}
