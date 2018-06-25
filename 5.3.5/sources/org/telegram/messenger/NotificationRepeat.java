package org.telegram.messenger;

import android.app.IntentService;
import android.content.Intent;

public class NotificationRepeat extends IntentService {

    /* renamed from: org.telegram.messenger.NotificationRepeat$1 */
    class C15801 implements Runnable {
        C15801() {
        }

        public void run() {
            NotificationsController.getInstance().repeatNotificationMaybe();
        }
    }

    public NotificationRepeat() {
        super("NotificationRepeat");
    }

    protected void onHandleIntent(Intent intent) {
        AndroidUtilities.runOnUIThread(new C15801());
    }
}
