package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class MediaController$4 extends BroadcastReceiver {
    final /* synthetic */ MediaController this$0;

    MediaController$4(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        this.this$0.checkAutodownloadSettings();
    }
}
