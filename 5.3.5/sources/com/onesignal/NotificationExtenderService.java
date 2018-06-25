package com.onesignal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.Extender;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.onesignal.OneSignal.LOG_LEVEL;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class NotificationExtenderService extends JobIntentService {
    static final int EXTENDER_SERVICE_JOB_ID = 2071862121;
    private OverrideSettings currentBaseOverrideSettings = null;
    private JSONObject currentJsonPayload;
    private boolean currentlyRestoring;
    private OSNotificationDisplayedResult osNotificationDisplayedResult;
    private Long restoreTimestamp;

    public static class OverrideSettings {
        public Integer androidNotificationId;
        public Extender extender;

        void override(OverrideSettings overrideSettings) {
            if (overrideSettings != null && overrideSettings.androidNotificationId != null) {
                this.androidNotificationId = overrideSettings.androidNotificationId;
            }
        }
    }

    protected abstract boolean onNotificationProcessing(OSNotificationReceivedResult oSNotificationReceivedResult);

    public /* bridge */ /* synthetic */ boolean isStopped() {
        return super.isStopped();
    }

    public /* bridge */ /* synthetic */ IBinder onBind(@NonNull Intent intent) {
        return super.onBind(intent);
    }

    public /* bridge */ /* synthetic */ void onCreate() {
        super.onCreate();
    }

    public /* bridge */ /* synthetic */ void onDestroy() {
        super.onDestroy();
    }

    public /* bridge */ /* synthetic */ int onStartCommand(@Nullable Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }

    public /* bridge */ /* synthetic */ boolean onStopCurrentWork() {
        return super.onStopCurrentWork();
    }

    public /* bridge */ /* synthetic */ void setInterruptIfStopped(boolean z) {
        super.setInterruptIfStopped(z);
    }

    protected final OSNotificationDisplayedResult displayNotification(OverrideSettings overrideSettings) {
        if (this.osNotificationDisplayedResult != null || overrideSettings == null) {
            return null;
        }
        overrideSettings.override(this.currentBaseOverrideSettings);
        this.osNotificationDisplayedResult = new OSNotificationDisplayedResult();
        NotificationGenerationJob notifJob = createNotifJobFromCurrent();
        notifJob.overrideSettings = overrideSettings;
        this.osNotificationDisplayedResult.androidNotificationId = NotificationBundleProcessor.ProcessJobForDisplay(notifJob);
        return this.osNotificationDisplayedResult;
    }

    protected final void onHandleWork(Intent intent) {
        if (intent != null) {
            processIntent(intent);
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            OneSignal.Log(LOG_LEVEL.ERROR, "No extras sent to NotificationExtenderService in its Intent!\n" + intent);
            return;
        }
        String jsonStrPayload = bundle.getString("json_payload");
        if (jsonStrPayload == null) {
            OneSignal.Log(LOG_LEVEL.ERROR, "json_payload key is nonexistent from bundle passed to NotificationExtenderService: " + bundle);
            return;
        }
        try {
            this.currentJsonPayload = new JSONObject(jsonStrPayload);
            this.currentlyRestoring = bundle.getBoolean("restoring", false);
            if (bundle.containsKey("android_notif_id")) {
                this.currentBaseOverrideSettings = new OverrideSettings();
                this.currentBaseOverrideSettings.androidNotificationId = Integer.valueOf(bundle.getInt("android_notif_id"));
            }
            if (this.currentlyRestoring || !OneSignal.notValidOrDuplicated(this, this.currentJsonPayload)) {
                this.restoreTimestamp = Long.valueOf(bundle.getLong(Param.TIMESTAMP));
                processJsonObject(this.currentJsonPayload, this.currentlyRestoring);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void processJsonObject(JSONObject currentJsonPayload, boolean restoring) {
        OSNotificationReceivedResult receivedResult = new OSNotificationReceivedResult();
        receivedResult.payload = NotificationBundleProcessor.OSNotificationPayloadFrom(currentJsonPayload);
        receivedResult.restoring = restoring;
        receivedResult.isAppInFocus = OneSignal.isAppActive();
        this.osNotificationDisplayedResult = null;
        boolean developerProcessed = false;
        try {
            developerProcessed = onNotificationProcessing(receivedResult);
        } catch (Throwable t) {
            if (this.osNotificationDisplayedResult == null) {
                OneSignal.Log(LOG_LEVEL.ERROR, "onNotificationProcessing throw an exception. Displaying normal OneSignal notification.", t);
            } else {
                OneSignal.Log(LOG_LEVEL.ERROR, "onNotificationProcessing throw an exception. Extended notification displayed but custom processing did not finish.", t);
            }
        }
        if (this.osNotificationDisplayedResult == null) {
            boolean display;
            if (developerProcessed || !NotificationBundleProcessor.shouldDisplay(currentJsonPayload.optString("alert"))) {
                display = false;
            } else {
                display = true;
            }
            if (display) {
                NotificationBundleProcessor.ProcessJobForDisplay(createNotifJobFromCurrent());
            } else if (!restoring) {
                NotificationGenerationJob notifJob = new NotificationGenerationJob(this);
                notifJob.jsonPayload = currentJsonPayload;
                notifJob.overrideSettings = new OverrideSettings();
                notifJob.overrideSettings.androidNotificationId = Integer.valueOf(-1);
                NotificationBundleProcessor.saveNotification(notifJob, true);
                OneSignal.handleNotificationReceived(NotificationBundleProcessor.newJsonArray(currentJsonPayload), false, false);
            } else if (this.currentBaseOverrideSettings != null) {
                NotificationBundleProcessor.markRestoredNotificationAsDismissed(createNotifJobFromCurrent());
            }
            if (restoring) {
                OSUtils.sleep(100);
            }
        }
    }

    static Intent getIntent(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent().setAction("com.onesignal.NotificationExtender").setPackage(context.getPackageName());
        List<ResolveInfo> resolveInfo = packageManager.queryIntentServices(intent, 128);
        if (resolveInfo.size() < 1) {
            return null;
        }
        intent.setComponent(new ComponentName(context, ((ResolveInfo) resolveInfo.get(0)).serviceInfo.name));
        return intent;
    }

    private NotificationGenerationJob createNotifJobFromCurrent() {
        NotificationGenerationJob notifJob = new NotificationGenerationJob(this);
        notifJob.restoring = this.currentlyRestoring;
        notifJob.jsonPayload = this.currentJsonPayload;
        notifJob.shownTimeStamp = this.restoreTimestamp;
        notifJob.overrideSettings = this.currentBaseOverrideSettings;
        return notifJob;
    }
}
