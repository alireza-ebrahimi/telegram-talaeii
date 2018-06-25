package net.hockeyapp.android.metrics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

@TargetApi(14)
class MetricsManager$TelemetryLifecycleCallbacks implements ActivityLifecycleCallbacks {
    final /* synthetic */ MetricsManager this$0;

    private MetricsManager$TelemetryLifecycleCallbacks(MetricsManager metricsManager) {
        this.this$0 = metricsManager;
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        MetricsManager.access$200(this.this$0);
    }

    public void onActivityPaused(Activity activity) {
        MetricsManager.LAST_BACKGROUND.set(MetricsManager.access$300());
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
