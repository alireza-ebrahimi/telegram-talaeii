package android.support.v4.content;

import android.content.Context;
import android.os.Binder;
import android.os.Process;
import android.support.v4.app.C0337j;

/* renamed from: android.support.v4.content.o */
public final class C0428o {
    /* renamed from: a */
    public static int m1906a(Context context, String str) {
        return C0428o.m1907a(context, str, Process.myPid(), Process.myUid(), context.getPackageName());
    }

    /* renamed from: a */
    public static int m1907a(Context context, String str, int i, int i2, String str2) {
        if (context.checkPermission(str, i, i2) == -1) {
            return -1;
        }
        String a = C0337j.m1455a(str);
        if (a == null) {
            return 0;
        }
        if (str2 == null) {
            String[] packagesForUid = context.getPackageManager().getPackagesForUid(i2);
            if (packagesForUid == null || packagesForUid.length <= 0) {
                return -1;
            }
            str2 = packagesForUid[0];
        }
        return C0337j.m1454a(context, a, str2) != 0 ? -2 : 0;
    }

    /* renamed from: a */
    public static int m1908a(Context context, String str, String str2) {
        return Binder.getCallingPid() == Process.myPid() ? -1 : C0428o.m1907a(context, str, Binder.getCallingPid(), Binder.getCallingUid(), str2);
    }

    /* renamed from: b */
    public static int m1909b(Context context, String str) {
        return C0428o.m1907a(context, str, Binder.getCallingPid(), Binder.getCallingUid(), Binder.getCallingPid() == Process.myPid() ? context.getPackageName() : null);
    }
}
