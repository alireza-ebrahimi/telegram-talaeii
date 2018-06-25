package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$132 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ Integer val$id;
    final /* synthetic */ int val$key;

    MessagesController$132(MessagesController this$0, int i, Integer num) {
        this.this$0 = this$0;
        this.val$key = i;
        this.val$id = num;
    }

    public void run() {
        MessagesStorage.getInstance().updateDialogsWithDeletedMessages(new ArrayList(), MessagesStorage.getInstance().markMessagesAsDeleted(this.val$key, this.val$id.intValue(), false), false, this.val$key);
    }
}
