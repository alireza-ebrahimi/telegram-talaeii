package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.TimeZone;

class LocaleController$TimeZoneChangedReceiver extends BroadcastReceiver {
    final /* synthetic */ LocaleController this$0;

    /* renamed from: org.telegram.messenger.LocaleController$TimeZoneChangedReceiver$1 */
    class C13911 implements Runnable {
        C13911() {
        }

        public void run() {
            if (!LocaleController$TimeZoneChangedReceiver.this.this$0.formatterMonth.getTimeZone().equals(TimeZone.getDefault())) {
                LocaleController.getInstance().recreateFormatters();
            }
        }
    }

    private LocaleController$TimeZoneChangedReceiver(LocaleController localeController) {
        this.this$0 = localeController;
    }

    public void onReceive(Context context, Intent intent) {
        ApplicationLoader.applicationHandler.post(new C13911());
    }
}
