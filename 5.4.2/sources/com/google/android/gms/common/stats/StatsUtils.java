package com.google.android.gms.common.stats;

import android.content.AbstractThreadedSyncAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.common.stats.StatisticalEventTrackerProvider.StatisticalEventTracker;

public class StatsUtils {
    public static String getEventKey(AbstractThreadedSyncAdapter abstractThreadedSyncAdapter, String str) {
        Object obj;
        String valueOf = String.valueOf(String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(abstractThreadedSyncAdapter))));
        if (TextUtils.isEmpty(str)) {
            obj = TtmlNode.ANONYMOUS_REGION_ID;
        }
        String valueOf2 = String.valueOf(obj);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public static String getEventKey(Context context, Intent intent) {
        return String.valueOf((((long) System.identityHashCode(context)) << 32) | ((long) System.identityHashCode(intent)));
    }

    public static String getEventKey(WakeLock wakeLock, String str) {
        Object obj;
        String valueOf = String.valueOf(String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(wakeLock))));
        if (TextUtils.isEmpty(str)) {
            obj = TtmlNode.ANONYMOUS_REGION_ID;
        }
        String valueOf2 = String.valueOf(obj);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public static boolean isLoggingEnabled() {
        StatisticalEventTracker impl = StatisticalEventTrackerProvider.getImpl();
        return impl != null && impl.isEnabled() && (zza(Integer.valueOf(impl.getLogLevel(3))) || zza(Integer.valueOf(impl.getLogLevel(2))) || zza(Integer.valueOf(impl.getLogLevel(1))));
    }

    public static boolean isTimeOutEvent(StatsEvent statsEvent) {
        switch (statsEvent.getEventType()) {
            case 6:
            case 9:
            case 12:
                return true;
            default:
                return false;
        }
    }

    private static boolean zza(Integer num) {
        return !num.equals(Integer.valueOf(LoggingConstants.LOG_LEVEL_OFF));
    }
}
