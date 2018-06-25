package org.telegram.customization.util.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import utils.view.Constants;

public class NotificationDismissReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals(Constants.INTENT_DISMISS_NOTIF)) {
            long reqId = -1;
            try {
                reqId = intent.getLongExtra(Constants.SLS_EXTRA_PUSH_RI, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (reqId >= 1) {
            }
        }
    }
}
