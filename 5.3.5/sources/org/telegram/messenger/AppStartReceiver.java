package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.telegram.customization.service.BaseService;
import org.telegram.customization.service.ProxyService;

public class AppStartReceiver extends BroadcastReceiver {
    public void onReceive(final Context context, Intent intent) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                BaseService.registerAllServices(context);
                ProxyService.startProxyService(context);
                ApplicationLoader.startPushService();
            }
        });
    }
}
