package org.telegram.customization.fetch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class FetchService$9 extends BroadcastReceiver {
    final /* synthetic */ FetchService this$0;

    FetchService$9(FetchService this$0) {
        this.this$0 = this$0;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            long id = FetchRunnable.getIdFromIntent(intent);
            if (FetchService.access$2200(this.this$0).containsKey(Long.valueOf(id))) {
                FetchService.access$2200(this.this$0).remove(Long.valueOf(id));
            }
            FetchService.access$700(this.this$0);
        }
    }
}
