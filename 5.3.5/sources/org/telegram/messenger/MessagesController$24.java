package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$24 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$channelId;
    final /* synthetic */ ArrayList val$oldTask;

    MessagesController$24(MessagesController this$0, ArrayList arrayList, int i) {
        this.this$0 = this$0;
        this.val$oldTask = arrayList;
        this.val$channelId = i;
    }

    public void run() {
        MessagesController.access$3402(this.this$0, true);
        MessagesStorage.getInstance().getNewTask(this.val$oldTask, this.val$channelId);
    }
}
