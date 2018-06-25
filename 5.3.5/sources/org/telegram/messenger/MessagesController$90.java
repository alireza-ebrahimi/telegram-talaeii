package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Updates;

class MessagesController$90 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    /* renamed from: org.telegram.messenger.MessagesController$90$1 */
    class C15171 implements Runnable {
        C15171() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(8192)});
        }
    }

    MessagesController$90(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            this.this$0.processUpdates((TLRPC$Updates) response, false);
            AndroidUtilities.runOnUIThread(new C15171());
        }
    }
}
