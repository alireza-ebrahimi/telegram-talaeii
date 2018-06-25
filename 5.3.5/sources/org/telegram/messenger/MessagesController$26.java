package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.ConnectionsManager;

class MessagesController$26 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$messages;
    final /* synthetic */ int val$taskTime;

    /* renamed from: org.telegram.messenger.MessagesController$26$1 */
    class C14801 implements Runnable {
        C14801() {
        }

        public void run() {
            MessagesController.access$3800(MessagesController$26.this.this$0, true);
        }
    }

    MessagesController$26(MessagesController this$0, ArrayList arrayList, int i) {
        this.this$0 = this$0;
        this.val$messages = arrayList;
        this.val$taskTime = i;
    }

    public void run() {
        MessagesController.access$3402(this.this$0, false);
        if (this.val$messages != null) {
            MessagesController.access$3502(this.this$0, this.val$taskTime);
            MessagesController.access$3302(this.this$0, this.val$messages);
            if (MessagesController.access$3700(this.this$0) != null) {
                Utilities.stageQueue.cancelRunnable(MessagesController.access$3700(this.this$0));
                MessagesController.access$3702(this.this$0, null);
            }
            if (!MessagesController.access$3800(this.this$0, false)) {
                MessagesController.access$3702(this.this$0, new C14801());
                Utilities.stageQueue.postRunnable(MessagesController.access$3700(this.this$0), ((long) Math.abs(ConnectionsManager.getInstance().getCurrentTime() - MessagesController.access$3500(this.this$0))) * 1000);
                return;
            }
            return;
        }
        MessagesController.access$3502(this.this$0, 0);
        MessagesController.access$3302(this.this$0, null);
    }
}
