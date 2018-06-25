package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import java.util.Calendar;
import org.telegram.customization.p151g.C2820e;
import utils.p178a.C3791b;

public class ProxyService extends C2827a {
    /* renamed from: a */
    public static Calendar m13189a() {
        return Calendar.getInstance();
    }

    /* renamed from: b */
    public static void m13190b(Context context) {
        C2827a.m13164a(context, new Intent(context, ProxyService.class), m13189a(), C3791b.m13924a());
    }

    /* renamed from: b */
    public static void m13191b(Context context, boolean z) {
        Intent intent = new Intent(context, ProxyService.class);
        intent.putExtra("EXTRA_IS_FORCE_GET_PROXY", z);
        context.startService(intent);
    }

    /* renamed from: c */
    public static void m13192c(Context context) {
        m13191b(context, false);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        boolean z = false;
        if (intent != null) {
            z = intent.getBooleanExtra("EXTRA_IS_FORCE_GET_PROXY", false);
        }
        if (C3791b.am(getApplicationContext()) > 0) {
            C2820e.m13151a(getApplicationContext(), z);
        }
        return 2;
    }
}
