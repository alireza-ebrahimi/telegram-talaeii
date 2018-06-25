package org.telegram.messenger;

class MessagesController$111 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$channelId;
    final /* synthetic */ boolean val$stop;

    MessagesController$111(MessagesController this$0, boolean z, int i) {
        this.this$0 = this$0;
        this.val$stop = z;
        this.val$channelId = i;
    }

    public void run() {
        if (this.val$stop) {
            MessagesController.access$1800(this.this$0).delete(this.val$channelId);
            return;
        }
        MessagesController.access$1800(this.this$0).put(this.val$channelId, 0);
        if (MessagesController.access$1700(this.this$0).indexOfKey(this.val$channelId) < 0) {
            this.this$0.getChannelDifference(this.val$channelId, 3, 0, null);
        }
    }
}
