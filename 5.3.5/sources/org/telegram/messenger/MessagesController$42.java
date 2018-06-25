package org.telegram.messenger;

class MessagesController$42 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$did;

    MessagesController$42(MessagesController this$0, long j) {
        this.this$0 = this$0;
        this.val$did = j;
    }

    public void run() {
        MessagesController.access$1600(this.this$0).remove(Integer.valueOf(-((int) this.val$did)));
        MessagesController.access$1700(this.this$0).delete(-((int) this.val$did));
        MessagesController.access$1800(this.this$0).delete(-((int) this.val$did));
    }
}
