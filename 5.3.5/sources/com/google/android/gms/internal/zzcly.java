package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.text.TextUtils;

@TargetApi(14)
@MainThread
final class zzcly implements ActivityLifecycleCallbacks {
    private /* synthetic */ zzclk zzjpy;

    private zzcly(zzclk zzclk) {
        this.zzjpy = zzclk;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        try {
            this.zzjpy.zzayp().zzbba().log("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null && data.isHierarchical()) {
                    if (bundle == null) {
                        Bundle zzp = this.zzjpy.zzayl().zzp(data);
                        this.zzjpy.zzayl();
                        String str = zzcno.zzn(intent) ? "gs" : "auto";
                        if (zzp != null) {
                            this.zzjpy.zzd(str, "_cmp", zzp);
                        }
                    }
                    CharSequence queryParameter = data.getQueryParameter("referrer");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        Object obj = (queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content"))) ? 1 : null;
                        if (obj == null) {
                            this.zzjpy.zzayp().zzbaz().log("Activity created with data 'referrer' param without gclid and at least one utm field");
                            return;
                        }
                        this.zzjpy.zzayp().zzbaz().zzj("Activity created with referrer", queryParameter);
                        if (!TextUtils.isEmpty(queryParameter)) {
                            this.zzjpy.zzb("auto", "_ldl", queryParameter);
                        }
                    } else {
                        return;
                    }
                }
            }
        } catch (Throwable th) {
            this.zzjpy.zzayp().zzbau().zzj("Throwable caught in onActivityCreated", th);
        }
        zzcma zzayh = this.zzjpy.zzayh();
        if (bundle != null) {
            Bundle bundle2 = bundle.getBundle("com.google.firebase.analytics.screen_service");
            if (bundle2 != null) {
                zzcmd zzr = zzayh.zzr(activity);
                zzr.zzjql = bundle2.getLong("id");
                zzr.zzjqj = bundle2.getString("name");
                zzr.zzjqk = bundle2.getString("referrer_name");
            }
        }
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzjpy.zzayh().onActivityDestroyed(activity);
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        this.zzjpy.zzayh().onActivityPaused(activity);
        zzclh zzayn = this.zzjpy.zzayn();
        zzayn.zzayo().zzh(new zzcnh(zzayn, zzayn.zzxx().elapsedRealtime()));
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        this.zzjpy.zzayh().onActivityResumed(activity);
        zzclh zzayn = this.zzjpy.zzayn();
        zzayn.zzayo().zzh(new zzcng(zzayn, zzayn.zzxx().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzjpy.zzayh().onActivitySaveInstanceState(activity, bundle);
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }
}
