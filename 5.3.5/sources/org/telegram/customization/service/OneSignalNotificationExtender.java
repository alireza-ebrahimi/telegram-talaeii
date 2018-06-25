package org.telegram.customization.service;

import android.util.Log;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;

public class OneSignalNotificationExtender extends NotificationExtenderService {
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
        Log.i("OneSignalExample", "OneSignalNotificationExtender");
        return true;
    }
}
