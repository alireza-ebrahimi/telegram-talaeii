package org.telegram.tgnet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class ConnectionsManager$2 extends BroadcastReceiver {
    final /* synthetic */ ConnectionsManager this$0;

    ConnectionsManager$2(ConnectionsManager this$0) {
        this.this$0 = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.this$0.checkConnection();
    }
}
