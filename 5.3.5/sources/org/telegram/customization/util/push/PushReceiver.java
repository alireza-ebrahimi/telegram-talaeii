package org.telegram.customization.util.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.telegram.customization.Model.HotgramNotification;

public class PushReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        long reqId = intent.getLongExtra("RequestId", -1);
        String msg = intent.getStringExtra("Message");
        Log.d("LEE", "aaaaliii" + msg);
        Log.d("slspushreceiver reqid: ", reqId + "");
        Log.d("slspushreceiver msg: ", msg);
        if (msg != null) {
            HotgramNotification.handlePush(context, msg, reqId);
        }
    }
}
