package org.telegram.customization.service;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.telegram.customization.Model.HotgramNotification;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    /* renamed from: a */
    public void mo3491a(RemoteMessage remoteMessage) {
        Log.d("LEE", "From: " + remoteMessage.m8911a());
        if (remoteMessage.m8912b().size() > 0) {
            Log.i("LEE", "Message data payload: " + remoteMessage.m8912b());
            HotgramNotification.handlePush(getApplicationContext(), (String) remoteMessage.m8912b().get("customkey"), 1369);
        }
        if (remoteMessage.m8913c() != null) {
            Log.i("LEE", "Message Notification Body: " + remoteMessage.m8913c().m8910a());
        }
    }
}
