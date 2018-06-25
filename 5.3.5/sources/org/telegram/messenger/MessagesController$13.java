package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$13 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$array;
    final /* synthetic */ boolean val$cache;
    final /* synthetic */ int val$chatId;

    MessagesController$13(MessagesController this$0, int i, ArrayList arrayList, boolean z) {
        this.this$0 = this$0;
        this.val$chatId = i;
        this.val$array = arrayList;
        this.val$cache = z;
    }

    public void run() {
        MessagesController.access$1900(this.this$0).delete(this.val$chatId);
        MessagesController.access$2000(this.this$0).put(Integer.valueOf(this.val$chatId), this.val$array);
        if (this.val$cache) {
            this.this$0.loadChannelAdmins(this.val$chatId, false);
        }
    }
}
