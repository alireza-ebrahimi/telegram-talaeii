package org.telegram.messenger;

import org.telegram.tgnet.ConnectionsManager;

class MessagesController$7 implements Runnable {
    final /* synthetic */ MessagesController this$0;

    MessagesController$7(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        ConnectionsManager.getInstance().setIsUpdating(false);
        MessagesController.access$1300(this.this$0).clear();
        MessagesController.access$1400(this.this$0).clear();
        MessagesController.access$1500(this.this$0).clear();
        MessagesController.access$1600(this.this$0).clear();
        MessagesController.access$1700(this.this$0).clear();
        MessagesController.access$1800(this.this$0).clear();
    }
}
