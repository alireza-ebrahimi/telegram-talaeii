package org.telegram.messenger.voip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class VoIPActionsReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (VoIPBaseService.getSharedInstance() != null) {
            VoIPBaseService.getSharedInstance().handleNotificationAction(intent);
        }
    }
}
