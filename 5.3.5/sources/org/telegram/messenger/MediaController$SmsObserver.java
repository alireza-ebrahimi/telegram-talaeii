package org.telegram.messenger;

import android.database.ContentObserver;

class MediaController$SmsObserver extends ContentObserver {
    final /* synthetic */ MediaController this$0;

    public MediaController$SmsObserver(MediaController mediaController) {
        this.this$0 = mediaController;
        super(null);
    }

    public void onChange(boolean selfChange) {
        MediaController.access$1400(this.this$0);
    }
}
