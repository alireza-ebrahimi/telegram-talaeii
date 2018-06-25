package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$117 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$chat_id;
    final /* synthetic */ ArrayList val$pushMessages;

    MessagesController$117(MessagesController this$0, int i, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$chat_id = i;
        this.val$pushMessages = arrayList;
    }

    public void run() {
        this.this$0.updateInterfaceWithMessages((long) (-this.val$chat_id), this.val$pushMessages);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
    }
}
