package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.util.List;

public final class zzfy {
    private final zzgm zzacw;
    @VisibleForTesting
    volatile zzr zzalf;
    @VisibleForTesting
    private ServiceConnection zzalg;

    zzfy(zzgm zzgm) {
        this.zzacw = zzgm;
    }

    @VisibleForTesting
    private final boolean zzjp() {
        try {
            PackageManagerWrapper packageManager = Wrappers.packageManager(this.zzacw.getContext());
            if (packageManager != null) {
                return packageManager.getPackageInfo("com.android.vending", 128).versionCode >= 80837300;
            } else {
                this.zzacw.zzgf().zzix().log("Failed to retrieve Package Manager to check Play Store compatibility");
                return false;
            }
        } catch (Exception e) {
            this.zzacw.zzgf().zzix().zzg("Failed to retrieve Play Store version", e);
            return false;
        }
    }

    @VisibleForTesting
    final void zzc(Bundle bundle) {
        this.zzacw.zzge().zzab();
        if (bundle != null) {
            long j = bundle.getLong("install_begin_timestamp_seconds", 0) * 1000;
            if (j == 0) {
                this.zzacw.zzgf().zzis().log("Service response is missing Install Referrer install timestamp");
                return;
            }
            String string = bundle.getString("install_referrer");
            if (string == null || string.isEmpty()) {
                this.zzacw.zzgf().zzis().log("No referrer defined in install referrer response");
                return;
            }
            this.zzacw.zzgf().zziz().zzg("InstallReferrer API result", string);
            zzkc zzgc = this.zzacw.zzgc();
            String str = "?";
            string = String.valueOf(string);
            Bundle zza = zzgc.zza(Uri.parse(string.length() != 0 ? str.concat(string) : new String(str)));
            if (zza == null) {
                this.zzacw.zzgf().zzis().log("No campaign params defined in install referrer result");
                return;
            }
            string = zza.getString(C1797b.MEDIUM);
            Object obj = (string == null || "(not set)".equalsIgnoreCase(string) || "organic".equalsIgnoreCase(string)) ? null : 1;
            if (obj != null) {
                long j2 = bundle.getLong("referrer_click_timestamp_seconds", 0) * 1000;
                if (j2 == 0) {
                    this.zzacw.zzgf().zzis().log("Install Referrer is missing click timestamp for ad campaign");
                    return;
                }
                zza.putLong("click_timestamp", j2);
            }
            if (j == this.zzacw.zzgg().zzakj.get()) {
                this.zzacw.zzgf().zziz().log("Campaign has already been logged");
                return;
            }
            zza.putString("_cis", "referrer API");
            this.zzacw.zzgg().zzakj.set(j);
            this.zzacw.zzfv().logEvent("auto", "_cmp", zza);
            if (this.zzalg != null) {
                ConnectionTracker.getInstance().unbindService(this.zzacw.getContext(), this.zzalg);
            }
        }
    }

    protected final void zzjo() {
        this.zzacw.zzgi();
        this.zzacw.zzge().zzab();
        if (zzjp()) {
            this.zzalg = new zzga();
            this.zzacw.zzgf().zzix().log("Install Referrer Reporter is initializing");
            this.zzacw.zzge().zzab();
            Intent intent = new Intent("com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE");
            intent.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.externalreferrer.GetInstallReferrerService"));
            PackageManager packageManager = this.zzacw.getContext().getPackageManager();
            if (packageManager == null) {
                this.zzacw.zzgf().zziv().log("Failed to obtain Package Manager to verify binding conditions");
                return;
            }
            List queryIntentServices = packageManager.queryIntentServices(intent, 0);
            if (queryIntentServices == null || queryIntentServices.isEmpty()) {
                this.zzacw.zzgf().zzix().log("Play Service for fetching Install Referrer is unavailable on device");
                return;
            }
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str = resolveInfo.serviceInfo.packageName;
                if (resolveInfo.serviceInfo.name == null || this.zzalg == null || !"com.android.vending".equals(str) || !zzjp()) {
                    this.zzacw.zzgf().zzix().log("Play Store missing or incompatible. Version 8.3.73 or later required");
                    return;
                }
                try {
                    this.zzacw.zzgf().zzix().zzg("Install Referrer Service is", ConnectionTracker.getInstance().bindService(this.zzacw.getContext(), new Intent(intent), this.zzalg, 1) ? "available" : "not available");
                    return;
                } catch (Exception e) {
                    this.zzacw.zzgf().zzis().zzg("Exception occurred while binding to Install Referrer Service", e.getMessage());
                    return;
                }
            }
            return;
        }
        this.zzacw.zzgf().zzix().log("Install Referrer Reporter is not available");
        this.zzalg = null;
    }

    @VisibleForTesting
    final Bundle zzjq() {
        this.zzacw.zzge().zzab();
        if (this.zzalf == null) {
            this.zzacw.zzgf().zziv().log("Attempting to use Install Referrer Service while it is not initialized");
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("package_name", this.zzacw.getContext().getPackageName());
        try {
            bundle = this.zzalf.zza(bundle);
            if (bundle != null) {
                return bundle;
            }
            this.zzacw.zzgf().zzis().log("Install Referrer Service returned a null response");
            return null;
        } catch (Exception e) {
            this.zzacw.zzgf().zzis().zzg("Exception occurred while retrieving the Install Referrer", e.getMessage());
            return null;
        }
    }
}
