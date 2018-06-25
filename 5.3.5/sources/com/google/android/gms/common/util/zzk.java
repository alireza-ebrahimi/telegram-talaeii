package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

public final class zzk {
    private static IntentFilter zzgkv = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long zzgkw;
    private static float zzgkx = Float.NaN;

    @TargetApi(20)
    public static int zzcy(Context context) {
        int i = 1;
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent registerReceiver = context.getApplicationContext().registerReceiver(null, zzgkv);
        int i2 = ((registerReceiver == null ? 0 : registerReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        int i3 = (zzs.zzanw() ? powerManager.isInteractive() : powerManager.isScreenOn() ? 1 : 0) << 1;
        if (i2 == 0) {
            i = 0;
        }
        return i3 | i;
    }

    public static synchronized float zzcz(Context context) {
        float f;
        synchronized (zzk.class) {
            if (SystemClock.elapsedRealtime() - zzgkw >= 60000 || Float.isNaN(zzgkx)) {
                Intent registerReceiver = context.getApplicationContext().registerReceiver(null, zzgkv);
                if (registerReceiver != null) {
                    zzgkx = ((float) registerReceiver.getIntExtra(Param.LEVEL, -1)) / ((float) registerReceiver.getIntExtra("scale", -1));
                }
                zzgkw = SystemClock.elapsedRealtime();
                f = zzgkx;
            } else {
                f = zzgkx;
            }
        }
        return f;
    }
}
