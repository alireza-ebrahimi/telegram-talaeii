package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$131 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$arrayList;
    final /* synthetic */ int val$key;

    MessagesController$131(MessagesController this$0, ArrayList arrayList, int i) {
        this.this$0 = this$0;
        this.val$arrayList = arrayList;
        this.val$key = i;
    }

    public void run() {
        MessagesStorage.getInstance().updateDialogsWithDeletedMessages(this.val$arrayList, MessagesStorage.getInstance().markMessagesAsDeleted(this.val$arrayList, false, this.val$key), false, this.val$key);
    }
}
