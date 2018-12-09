package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver.PendingResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;

public final class zzgc {
    private final zzgf zzalj;

    public zzgc(zzgf zzgf) {
        Preconditions.checkNotNull(zzgf);
        this.zzalj = zzgf;
    }

    public static boolean zza(Context context) {
        Preconditions.checkNotNull(context);
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

    public final void onReceive(Context context, Intent intent) {
        zzgm zza = zzgm.zza(context, null, null);
        zzfh zzgf = zza.zzgf();
        if (intent == null) {
            zzgf.zziv().log("Receiver called with null intent");
            return;
        }
        zza.zzgi();
        String action = intent.getAction();
        zzgf.zziz().zzg("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzgf.zziz().log("Starting wakeful intent.");
            this.zzalj.doStartService(context, className);
        } else if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            try {
                zza.zzge().zzc(new zzgd(this, zza, zzgf));
            } catch (Exception e) {
                zzgf.zziv().zzg("Install Referrer Reporter encountered a problem", e);
            }
            PendingResult doGoAsync = this.zzalj.doGoAsync();
            action = intent.getStringExtra("referrer");
            if (action == null) {
                zzgf.zziz().log("Install referrer extras are null");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
                return;
            }
            zzgf.zzix().zzg("Install referrer extras are", action);
            if (!action.contains("?")) {
                String str = "?";
                action = String.valueOf(action);
                action = action.length() != 0 ? str.concat(action) : new String(str);
            }
            Bundle zza2 = zza.zzgc().zza(Uri.parse(action));
            if (zza2 == null) {
                zzgf.zziz().log("No campaign defined in install referrer broadcast");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
                return;
            }
            long longExtra = 1000 * intent.getLongExtra("referrer_timestamp_seconds", 0);
            if (longExtra == 0) {
                zzgf.zziv().log("Install referrer is missing timestamp");
            }
            zza.zzge().zzc(new zzge(this, zza, longExtra, zza2, context, zzgf, doGoAsync));
        }
    }
}
