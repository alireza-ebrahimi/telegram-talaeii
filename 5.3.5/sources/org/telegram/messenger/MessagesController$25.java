package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$25 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$mids;

    /* renamed from: org.telegram.messenger.MessagesController$25$1 */
    class C14791 implements Runnable {
        C14791() {
        }

        public void run() {
            MessagesController$25.this.this$0.getNewDeleteTask(MessagesController$25.this.val$mids, MessagesController.access$3600(MessagesController$25.this.this$0));
            MessagesController.access$3502(MessagesController$25.this.this$0, 0);
            MessagesController.access$3302(MessagesController$25.this.this$0, null);
        }
    }

    MessagesController$25(MessagesController this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$mids = arrayList;
    }

    public void run() {
        if (this.val$mids.isEmpty() || ((Integer) this.val$mids.get(0)).intValue() <= 0) {
            this.this$0.deleteMessages(this.val$mids, null, null, 0, false);
        } else {
            MessagesStorage.getInstance().emptyMessagesMedia(this.val$mids);
        }
        Utilities.stageQueue.postRunnable(new C14791());
    }
}
