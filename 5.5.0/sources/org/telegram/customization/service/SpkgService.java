package org.telegram.customization.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SpkgService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Intent intent2 = new Intent("android.intent.action.MAIN", null);
        intent2.addCategory("android.intent.category.LAUNCHER");
        getPackageManager().queryIntentActivities(intent2, 0);
        return 2;
    }
}
