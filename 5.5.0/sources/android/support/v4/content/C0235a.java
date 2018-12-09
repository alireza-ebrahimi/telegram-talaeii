package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.p014d.C0432c;
import android.util.Log;
import android.util.TypedValue;
import java.io.File;

/* renamed from: android.support.v4.content.a */
public class C0235a {
    /* renamed from: a */
    private static final Object f769a = new Object();
    /* renamed from: b */
    private static TypedValue f770b;

    /* renamed from: a */
    public static final Drawable m1066a(Context context, int i) {
        int i2 = VERSION.SDK_INT;
        if (i2 >= 21) {
            return C0407b.m1871a(context, i);
        }
        if (i2 >= 16) {
            return context.getResources().getDrawable(i);
        }
        synchronized (f769a) {
            if (f770b == null) {
                f770b = new TypedValue();
            }
            context.getResources().getValue(i, f770b, true);
            i2 = f770b.resourceId;
        }
        return context.getResources().getDrawable(i2);
    }

    /* renamed from: a */
    private static synchronized File m1067a(File file) {
        synchronized (C0235a.class) {
            if (!(file.exists() || file.mkdirs() || file.exists())) {
                Log.w("ContextCompat", "Unable to create files subdir " + file.getPath());
                file = null;
            }
        }
        return file;
    }

    /* renamed from: a */
    public static void m1068a(Context context, Intent intent, Bundle bundle) {
        if (VERSION.SDK_INT >= 16) {
            C0411f.m1877a(context, intent, bundle);
        } else {
            context.startActivity(intent);
        }
    }

    /* renamed from: a */
    public static boolean m1069a(Context context, Intent[] intentArr, Bundle bundle) {
        int i = VERSION.SDK_INT;
        if (i >= 16) {
            C0411f.m1878a(context, intentArr, bundle);
            return true;
        } else if (i < 11) {
            return false;
        } else {
            C0410e.m1876a(context, intentArr);
            return true;
        }
    }

    /* renamed from: a */
    public static File[] m1070a(Context context) {
        if (VERSION.SDK_INT >= 19) {
            return C0412g.m1879a(context);
        }
        return new File[]{context.getExternalCacheDir()};
    }

    /* renamed from: a */
    public static File[] m1071a(Context context, String str) {
        if (VERSION.SDK_INT >= 19) {
            return C0412g.m1880a(context, str);
        }
        return new File[]{context.getExternalFilesDir(str)};
    }

    /* renamed from: b */
    public static int m1072b(Context context, String str) {
        if (str != null) {
            return context.checkPermission(str, Process.myPid(), Process.myUid());
        }
        throw new IllegalArgumentException("permission is null");
    }

    /* renamed from: b */
    public static final ColorStateList m1073b(Context context, int i) {
        return VERSION.SDK_INT >= 23 ? C0408c.m1873a(context, i) : context.getResources().getColorStateList(i);
    }

    /* renamed from: b */
    public static final File m1074b(Context context) {
        return VERSION.SDK_INT >= 21 ? C0407b.m1872a(context) : C0235a.m1067a(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    /* renamed from: c */
    public static final int m1075c(Context context, int i) {
        return VERSION.SDK_INT >= 23 ? C0408c.m1874b(context, i) : context.getResources().getColor(i);
    }

    /* renamed from: c */
    public static boolean m1076c(Context context) {
        return C0432c.m1912a() ? C0409d.m1875a(context) : false;
    }
}
