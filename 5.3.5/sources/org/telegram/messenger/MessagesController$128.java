package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$128 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$pushMessages;

    /* renamed from: org.telegram.messenger.MessagesController$128$1 */
    class C14631 implements Runnable {
        C14631() {
        }

        public void run() {
            NotificationsController.getInstance().processNewMessages(MessagesController$128.this.val$pushMessages, true);
        }
    }

    MessagesController$128(MessagesController this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$pushMessages = arrayList;
    }

    public void run() {
        AndroidUtilities.runOnUIThread(new C14631());
    }
}
