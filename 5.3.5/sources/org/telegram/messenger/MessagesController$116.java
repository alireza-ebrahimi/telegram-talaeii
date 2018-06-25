package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$116 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$pushMessages;

    /* renamed from: org.telegram.messenger.MessagesController$116$1 */
    class C14551 implements Runnable {
        C14551() {
        }

        public void run() {
            NotificationsController.getInstance().processNewMessages(MessagesController$116.this.val$pushMessages, true);
        }
    }

    MessagesController$116(MessagesController this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$pushMessages = arrayList;
    }

    public void run() {
        AndroidUtilities.runOnUIThread(new C14551());
    }
}
