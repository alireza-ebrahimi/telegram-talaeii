package com.onesignal;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GcmIntentService extends IntentService {
    public GcmIntentService() {
        super("GcmIntentService");
        setIntentRedelivery(true);
    }

    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            NotificationBundleProcessor.ProcessFromGCMIntentService(this, new BundleCompatBundle(extras), null);
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }
}
