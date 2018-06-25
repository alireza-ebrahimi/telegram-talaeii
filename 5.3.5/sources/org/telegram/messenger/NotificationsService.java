package org.telegram.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationsService extends Service {
    public void onCreate() {
        FileLog.m92e("service started");
        ApplicationLoader.postInitApplication();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        FileLog.m92e("service destroyed");
        if (ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("pushService", true)) {
            sendBroadcast(new Intent("org.telegram.start"));
        }
    }
}
