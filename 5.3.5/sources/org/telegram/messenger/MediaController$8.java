package org.telegram.messenger;

import java.util.ArrayList;

class MediaController$8 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ ArrayList val$screenshotDates;

    MediaController$8(MediaController this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$screenshotDates = arrayList;
    }

    public void run() {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.screenshotTook, new Object[0]);
        MediaController.access$3500(this.this$0, this.val$screenshotDates);
    }
}
