package org.telegram.customization.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import org.telegram.messenger.FileLog;
import utils.FreeDownload;
import utils.app.AppPreferences;

public class NetworkReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Bundle bundles = intent.getExtras();
        if (bundles != null && bundles.containsKey("networkInfo")) {
            NetworkInfo info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            if (info == null) {
                return;
            }
            if (info.isConnected()) {
                String previousType = AppPreferences.getNetworkType(context);
                String newType = info.getTypeName();
                AppPreferences.setNetworkType(context, newType);
                if (!(TextUtils.isEmpty(previousType) || TextUtils.isEmpty(newType) || previousType.equals(newType))) {
                    FreeDownload.updateAll(context);
                }
                FileLog.d("cchange NETCHANGE:   connected " + info.getTypeName());
                return;
            }
            FileLog.d("cchange NETCHANGE:   disconnected " + info.getTypeName());
        }
    }
}
