package org.telegram.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationsService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        FileLog.m13726e("service started");
        ApplicationLoader.postInitApplication();
    }

    public void onDestroy() {
        FileLog.m13726e("service destroyed");
        if (ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("pushService", true)) {
            sendBroadcast(new Intent("org.telegram.start"));
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }
}
