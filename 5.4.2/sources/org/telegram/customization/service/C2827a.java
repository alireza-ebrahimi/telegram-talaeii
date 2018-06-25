package org.telegram.customization.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

/* renamed from: org.telegram.customization.service.a */
public abstract class C2827a extends Service {
    /* renamed from: a */
    public static void m13162a(Context context) {
        BgtsService.m13170b(context);
        CtsService.m13174b(context);
        LocationService.m13185b(context);
        SgsService.m13194b(context);
        BgsService.m13167b(context);
        ProxyService.m13190b(context);
        UStateService.m13197b(context);
    }

    /* renamed from: a */
    public static void m13163a(Context context, Intent intent) {
        try {
            ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getService(context, 0, intent, 0));
        } catch (Exception e) {
        }
    }

    /* renamed from: a */
    public static void m13164a(Context context, Intent intent, Calendar calendar, long j) {
        PendingIntent service = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        try {
            alarmManager.cancel(service);
        } catch (Exception e) {
        }
        alarmManager.setRepeating(0, calendar.getTimeInMillis(), j, service);
    }

    /* renamed from: a */
    public static void m13165a(Context context, boolean z) {
        Intent intent = new Intent(context, BgtsService.class);
        intent.putExtra("EXTRA_IS_FORCE", z);
        context.startService(intent);
        intent = new Intent(context, CtsService.class);
        intent.putExtra("EXTRA_IS_FORCE", z);
        context.startService(intent);
        intent = new Intent(context, LocationService.class);
        intent.putExtra("EXTRA_IS_FORCE", z);
        context.startService(intent);
        intent = new Intent(context, SgsService.class);
        intent.putExtra("EXTRA_IS_FORCE", z);
        context.startService(intent);
        intent = new Intent(context, BgsService.class);
        intent.putExtra("EXTRA_IS_FORCE", z);
        context.startService(intent);
        intent = new Intent(context, ProxyService.class);
        intent.putExtra("EXTRA_IS_FORCE", z);
        context.startService(intent);
        intent = new Intent(context, UStateService.class);
        intent.putExtra("EXTRA_IS_FORCE", z);
        context.startService(intent);
    }
}
