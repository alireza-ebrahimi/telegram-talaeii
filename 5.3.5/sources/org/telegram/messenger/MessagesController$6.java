package org.telegram.messenger;

class MessagesController$6 implements Runnable {
    final /* synthetic */ MessagesController this$0;

    MessagesController$6(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        MessagesController.access$300(this.this$0).clear();
        MessagesController.access$400(this.this$0).clear();
        MessagesController.access$500(this.this$0).clear();
        MessagesController.access$600(this.this$0).clear();
        MessagesController.access$702(this.this$0, 0);
        MessagesController.access$802(this.this$0, 0);
        MessagesController.access$902(this.this$0, 0);
        MessagesController.access$1000(this.this$0).clear();
        this.this$0.gettingDifference = false;
        MessagesController.access$1102(this.this$0, null);
        MessagesController.access$1202(this.this$0, null);
    }
}
