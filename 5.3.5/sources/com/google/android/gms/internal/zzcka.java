package com.google.android.gms.internal;

import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

public final class zzcka {
    private final zzckc zzjmp;

    public zzcka(zzckc zzckc) {
        zzbq.checkNotNull(zzckc);
        this.zzjmp = zzckc;
    }

    @Hide
    public static boolean zzbj(Context context) {
        zzbq.checkNotNull(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            ActivityInfo receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 0);
            return receiverInfo != null && receiverInfo.enabled;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        zzckj zzed = zzckj.zzed(context);
        zzcjj zzayp = zzed.zzayp();
        if (intent == null) {
            zzayp.zzbaw().log("Receiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzayp.zzbba().zzj("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzayp.zzbba().log("Starting wakeful intent.");
            this.zzjmp.doStartService(context, className);
        } else if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            PendingResult doGoAsync = this.zzjmp.doGoAsync();
            action = intent.getStringExtra("referrer");
            if (action == null) {
                zzayp.zzbba().log("Install referrer extras are null");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
                return;
            }
            zzayp.zzbay().zzj("Install referrer extras are", action);
            if (!action.contains("?")) {
                String str = "?";
                action = String.valueOf(action);
                action = action.length() != 0 ? str.concat(action) : new String(str);
            }
            Bundle zzp = zzed.zzayl().zzp(Uri.parse(action));
            if (zzp == null) {
                zzayp.zzbba().log("No campaign defined in install referrer broadcast");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
                return;
            }
            long longExtra = 1000 * intent.getLongExtra("referrer_timestamp_seconds", 0);
            if (longExtra == 0) {
                zzayp.zzbaw().log("Install referrer is missing timestamp");
            }
            zzed.zzayo().zzh(new zzckb(this, zzed, longExtra, zzp, context, zzayp, doGoAsync));
        }
    }
}
