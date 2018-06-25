package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.telegram.customization.service.C2827a;
import org.telegram.customization.service.ProxyService;

public class AppStartReceiver extends BroadcastReceiver {
    public void onReceive(final Context context, Intent intent) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                C2827a.m13162a(context);
                ProxyService.m13192c(context);
                ApplicationLoader.startPushService();
            }
        });
    }
}
