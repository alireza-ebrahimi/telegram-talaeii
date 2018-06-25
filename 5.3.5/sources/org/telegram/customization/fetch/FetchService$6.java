package org.telegram.customization.fetch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class FetchService$6 extends BroadcastReceiver {
    final /* synthetic */ FetchService this$0;

    FetchService$6(FetchService this$0) {
        this.this$0 = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            FetchService.access$2400(this.this$0, FetchRunnable.getIdFromIntent(intent));
        }
        if (FetchService.access$2200(this.this$0).size() == 0) {
            FetchService.access$2500(this.this$0);
            FetchService.access$1800(this.this$0).unregisterReceiver(this);
            FetchService.access$1900(this.this$0).remove(this);
            FetchService.access$2002(this.this$0, false);
            FetchService.access$700(this.this$0);
        }
    }
}
