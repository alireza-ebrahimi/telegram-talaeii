package org.telegram.messenger;

import org.telegram.customization.Internet.IResponseReceiver;

class MessagesController$97 implements IResponseReceiver {
    final /* synthetic */ MessagesController this$0;

    MessagesController$97(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void onResult(Object object, int StatusCode) {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
    }
}
