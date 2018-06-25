package org.telegram.messenger;

class MessagesController$8 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ boolean val$set;

    MessagesController$8(MessagesController this$0, boolean z, long j) {
        this.this$0 = this$0;
        this.val$set = z;
        this.val$dialog_id = j;
    }

    public void run() {
        if (this.val$set) {
            MessagesController.access$1000(this.this$0).add(Long.valueOf(this.val$dialog_id));
        } else {
            MessagesController.access$1000(this.this$0).remove(Long.valueOf(this.val$dialog_id));
        }
    }
}
