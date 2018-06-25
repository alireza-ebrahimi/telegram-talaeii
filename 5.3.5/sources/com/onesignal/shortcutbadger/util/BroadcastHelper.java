package com.onesignal.shortcutbadger.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import java.util.List;

public class BroadcastHelper {
    public static boolean canResolveBroadcast(Context context, Intent intent) {
        List<ResolveInfo> receivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (receivers == null || receivers.size() <= 0) {
            return false;
        }
        return true;
    }
}
