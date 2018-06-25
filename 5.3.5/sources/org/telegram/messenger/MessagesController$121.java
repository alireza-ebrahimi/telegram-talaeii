package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$121 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$objArr;

    /* renamed from: org.telegram.messenger.MessagesController$121$1 */
    class C14611 implements Runnable {
        C14611() {
        }

        public void run() {
            NotificationsController.getInstance().processNewMessages(MessagesController$121.this.val$objArr, true);
        }
    }

    MessagesController$121(MessagesController this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$objArr = arrayList;
    }

    public void run() {
        AndroidUtilities.runOnUIThread(new C14611());
    }
}
