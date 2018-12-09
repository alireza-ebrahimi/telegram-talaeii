package org.telegram.customization.util.p169a;

import android.app.NotificationManager;
import android.content.Context;

/* renamed from: org.telegram.customization.util.a.a */
public class C2860a {
    /* renamed from: a */
    private static NotificationManager f9467a;

    /* renamed from: a */
    private static NotificationManager m13318a(Context context) {
        if (f9467a == null) {
            f9467a = (NotificationManager) context.getSystemService("notification");
        }
        return f9467a;
    }

    /* renamed from: a */
    public static void m13319a(Context context, int i) {
        C2860a.m13318a(context).cancel(i);
    }
}
