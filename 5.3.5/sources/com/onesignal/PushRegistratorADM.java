package com.onesignal;

import android.content.Context;
import com.amazon.device.messaging.ADM;
import com.onesignal.OneSignal.LOG_LEVEL;
import com.onesignal.PushRegistrator.RegisteredHandler;

public class PushRegistratorADM implements PushRegistrator {
    private static boolean callbackSuccessful = false;
    private static RegisteredHandler registeredCallback;

    public void registerForPush(final Context context, String noKeyNeeded, final RegisteredHandler callback) {
        registeredCallback = callback;
        new Thread(new Runnable() {
            public void run() {
                ADM adm = new ADM(context);
                String registrationId = adm.getRegistrationId();
                if (registrationId == null) {
                    adm.startRegister();
                } else {
                    OneSignal.Log(LOG_LEVEL.DEBUG, "ADM Already registered with ID:" + registrationId);
                    callback.complete(registrationId, 1);
                }
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                }
                if (!PushRegistratorADM.callbackSuccessful) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "com.onesignal.ADMMessageHandler timed out, please check that your have the receiver, service, and your package name matches(NOTE: Case Sensitive) per the OneSignal instructions.");
                    PushRegistratorADM.fireCallback(null);
                }
            }
        }).start();
    }

    public static void fireCallback(String id) {
        if (registeredCallback != null) {
            callbackSuccessful = true;
            registeredCallback.complete(id, 1);
        }
    }
}
