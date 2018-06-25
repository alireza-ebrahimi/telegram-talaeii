package org.telegram.messenger;

class MessagesController$53 implements Runnable {
    final /* synthetic */ MessagesController this$0;

    MessagesController$53(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(4)});
    }
}
