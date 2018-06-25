package com.onesignal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class SyncService extends Service {
    public int onStartCommand(Intent intent, int flags, int startId) {
        OneSignalSyncServiceUtils.doBackgroundSync(this, new LegacySyncRunnable(this));
        return 1;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
