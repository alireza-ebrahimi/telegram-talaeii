package org.telegram.customization.recievers;

import android.util.Log;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal.NotificationReceivedHandler;
import org.json.JSONObject;
import org.telegram.customization.Model.HotgramNotification;
import org.telegram.messenger.ApplicationLoader;

public class HotgramOneSignalNotificationReceivedHandler implements NotificationReceivedHandler {
    public void notificationReceived(OSNotification notification) {
        Log.d("LEE", "Alireza");
        JSONObject data = notification.payload.additionalData;
        if (data != null) {
            String customKey = data.optString("customkey", null);
            if (customKey != null) {
                Log.i("OneSignalExample", "customkey set with value: " + customKey);
                HotgramNotification.handlePush(ApplicationLoader.applicationContext, customKey, 1369);
            }
        }
    }
}
