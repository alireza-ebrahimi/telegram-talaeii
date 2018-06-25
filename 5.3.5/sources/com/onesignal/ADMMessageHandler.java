package com.onesignal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.amazon.device.messaging.ADMMessageHandlerBase;
import com.amazon.device.messaging.ADMMessageReceiver;
import com.onesignal.OneSignal.LOG_LEVEL;

public class ADMMessageHandler extends ADMMessageHandlerBase {

    public static class Receiver extends ADMMessageReceiver {
        public Receiver() {
            super(ADMMessageHandler.class);
        }
    }

    public ADMMessageHandler() {
        super("ADMMessageHandler");
    }

    protected void onMessage(Intent intent) {
        Context context = getApplicationContext();
        Bundle bundle = intent.getExtras();
        if (!NotificationBundleProcessor.processBundleFromReceiver(context, bundle).processed()) {
            NotificationGenerationJob notifJob = new NotificationGenerationJob(context);
            notifJob.jsonPayload = NotificationBundleProcessor.bundleAsJSONObject(bundle);
            NotificationBundleProcessor.ProcessJobForDisplay(notifJob);
        }
    }

    protected void onRegistered(String newRegistrationId) {
        OneSignal.Log(LOG_LEVEL.INFO, "ADM registration ID: " + newRegistrationId);
        PushRegistratorADM.fireCallback(newRegistrationId);
    }

    protected void onRegistrationError(String error) {
        OneSignal.Log(LOG_LEVEL.ERROR, "ADM:onRegistrationError: " + error);
        if ("INVALID_SENDER".equals(error)) {
            OneSignal.Log(LOG_LEVEL.ERROR, "Please double check that you have a matching package name (NOTE: Case Sensitive), api_key.txt, and the apk was signed with the same Keystore and Alias.");
        }
        PushRegistratorADM.fireCallback(null);
    }

    protected void onUnregistered(String info) {
        OneSignal.Log(LOG_LEVEL.INFO, "ADM:onUnregistered: " + info);
    }
}
