package com.google.android.gms.internal.measurement;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

@TargetApi(14)
final class zzie implements ActivityLifecycleCallbacks {
    private final /* synthetic */ zzhl zzaog;

    private zzie(zzhl zzhl) {
        this.zzaog = zzhl;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        try {
            this.zzaog.zzgf().zziz().log("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null && data.isHierarchical()) {
                    if (bundle == null) {
                        Bundle zza = this.zzaog.zzgc().zza(data);
                        this.zzaog.zzgc();
                        String str = zzkc.zzd(intent) ? "gs" : "auto";
                        if (zza != null) {
                            this.zzaog.logEvent(str, "_cmp", zza);
                        }
                    }
                    CharSequence queryParameter = data.getQueryParameter("referrer");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        Object obj = (queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content"))) ? 1 : null;
                        if (obj == null) {
                            this.zzaog.zzgf().zziy().log("Activity created with data 'referrer' param without gclid and at least one utm field");
                            return;
                        }
                        this.zzaog.zzgf().zziy().zzg("Activity created with referrer", queryParameter);
                        if (!TextUtils.isEmpty(queryParameter)) {
                            this.zzaog.setUserProperty("auto", "_ldl", queryParameter);
                        }
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            this.zzaog.zzgf().zzis().zzg("Throwable caught in onActivityCreated", e);
        }
        this.zzaog.zzfz().onActivityCreated(activity, bundle);
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzaog.zzfz().onActivityDestroyed(activity);
    }

    public final void onActivityPaused(Activity activity) {
        this.zzaog.zzfz().onActivityPaused(activity);
        zzhh zzgd = this.zzaog.zzgd();
        zzgd.zzge().zzc(new zzjm(zzgd, zzgd.zzbt().elapsedRealtime()));
    }

    public final void onActivityResumed(Activity activity) {
        this.zzaog.zzfz().onActivityResumed(activity);
        zzhh zzgd = this.zzaog.zzgd();
        zzgd.zzge().zzc(new zzjl(zzgd, zzgd.zzbt().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzaog.zzfz().onActivitySaveInstanceState(activity, bundle);
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }
}
