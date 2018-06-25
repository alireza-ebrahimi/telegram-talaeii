package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationDismissReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("dismissDate", intent.getIntExtra("messageDate", 0)).commit();
    }
}
