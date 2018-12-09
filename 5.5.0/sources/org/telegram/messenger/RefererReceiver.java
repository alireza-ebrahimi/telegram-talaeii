package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RefererReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            MessagesController.getInstance().setReferer(intent.getExtras().getString("referrer"));
        } catch (Exception e) {
        }
    }
}
