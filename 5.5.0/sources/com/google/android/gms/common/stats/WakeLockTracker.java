package com.google.android.gms.common.stats;

import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.DeviceStateUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Arrays;
import java.util.List;

public class WakeLockTracker {
    @VisibleForTesting
    private static boolean zzyh = false;
    private static WakeLockTracker zzyz = new WakeLockTracker();
    private static Boolean zzza;

    public static WakeLockTracker getInstance() {
        return zzyz;
    }

    public void registerAcquireEvent(Context context, Intent intent, String str, String str2, String str3, int i, String str4) {
        registerAcquireEvent(context, intent, str, str2, str3, i, Arrays.asList(new String[]{str4}));
    }

    public void registerAcquireEvent(Context context, Intent intent, String str, String str2, String str3, int i, List<String> list) {
        registerEvent(context, intent.getStringExtra(LoggingConstants.EXTRA_WAKE_LOCK_KEY), 7, str, str2, str3, i, list);
    }

    public void registerEvent(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list) {
        registerEvent(context, str, i, str2, str3, str4, i2, list, 0);
    }

    public void registerEvent(Context context, String str, int i, String str2, String str3, String str4, int i2, List<String> list, long j) {
        if (zzza == null) {
            zzza = Boolean.valueOf(false);
        }
        if (!zzza.booleanValue()) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            String str5 = "WakeLockTracker";
            String str6 = "missing wakeLock key. ";
            String valueOf = String.valueOf(str);
            Log.e(str5, valueOf.length() != 0 ? str6.concat(valueOf) : new String(str6));
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (7 == i || 8 == i || 10 == i || 11 == i) {
            List list2;
            if (list == null || list.size() != 1) {
                List<String> list3 = list;
            } else {
                if ("com.google.android.gms".equals(list.get(0))) {
                    list = null;
                }
                list2 = list;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            int deviceState = DeviceStateUtils.getDeviceState(context);
            String packageName = context.getPackageName();
            if ("com.google.android.gms".equals(packageName)) {
                packageName = null;
            }
            try {
                context.startService(new Intent().setComponent(LoggingConstants.STATS_SERVICE_COMPONENT_NAME).putExtra(LoggingConstants.EXTRA_LOG_EVENT, new WakeLockEvent(currentTimeMillis, i, str2, i2, list2, str, elapsedRealtime, deviceState, str3, packageName, DeviceStateUtils.getPowerPercentage(context), j, str4)));
            } catch (Throwable e) {
                Log.wtf("WakeLockTracker", e);
            }
        }
    }

    public void registerReleaseEvent(Context context, Intent intent) {
        registerEvent(context, intent.getStringExtra(LoggingConstants.EXTRA_WAKE_LOCK_KEY), 8, null, null, null, 0, null);
    }

    public void registerSyncEnd(Context context, AbstractThreadedSyncAdapter abstractThreadedSyncAdapter, String str, String str2, boolean z) {
        registerEvent(context, StatsUtils.getEventKey(abstractThreadedSyncAdapter, str), 11, str, str2, null, 0, null);
    }

    public void registerSyncStart(Context context, AbstractThreadedSyncAdapter abstractThreadedSyncAdapter, String str, String str2) {
        registerEvent(context, StatsUtils.getEventKey(abstractThreadedSyncAdapter, str), 10, str, str2, null, 0, null);
    }
}
