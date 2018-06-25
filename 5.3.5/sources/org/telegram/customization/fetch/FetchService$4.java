package org.telegram.customization.fetch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class FetchService$4 extends BroadcastReceiver {
    final /* synthetic */ FetchService this$0;
    final /* synthetic */ long val$id;

    FetchService$4(FetchService this$0, long j) {
        this.this$0 = this$0;
        this.val$id = j;
    }

    public void onReceive(Context context, Intent intent) {
        if (FetchRunnable.getIdFromIntent(intent) == this.val$id) {
            FetchService.access$2100(this.this$0, this.val$id);
            FetchService.access$1800(this.this$0).unregisterReceiver(this);
            FetchService.access$1900(this.this$0).remove(this);
            FetchService.access$2002(this.this$0, false);
            FetchService.access$700(this.this$0);
        }
    }
}
