package org.telegram.messenger;

import android.database.ContentObserver;
import android.os.Handler;

class AndroidUtilities$2 extends ContentObserver {
    final /* synthetic */ String val$number;

    AndroidUtilities$2(Handler x0, String str) {
        this.val$number = str;
        super(x0);
    }

    public boolean deliverSelfNotifications() {
        return true;
    }

    public void onChange(boolean selfChange) {
        AndroidUtilities.access$000(false, this.val$number);
        AndroidUtilities.removeLoginPhoneCall(this.val$number, false);
    }
}
