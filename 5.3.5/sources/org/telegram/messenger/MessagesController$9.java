package org.telegram.messenger;

class MessagesController$9 implements Runnable {
    final /* synthetic */ MessagesController this$0;

    MessagesController$9(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(4)});
    }
}
