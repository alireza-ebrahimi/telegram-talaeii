package org.telegram.messenger;

class MessagesController$22 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$minDate;

    MessagesController$22(MessagesController this$0, int i) {
        this.this$0 = this$0;
        this.val$minDate = i;
    }

    public void run() {
        if ((MessagesController.access$3300(this.this$0) == null && !MessagesController.access$3400(this.this$0)) || (MessagesController.access$3500(this.this$0) != 0 && this.val$minDate < MessagesController.access$3500(this.this$0))) {
            this.this$0.getNewDeleteTask(null, 0);
        }
    }
}
