package org.telegram.messenger;

import java.util.HashMap;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;

class MessagesController$56 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$action;
    final /* synthetic */ long val$dialog_id;

    /* renamed from: org.telegram.messenger.MessagesController$56$1 */
    class C14901 implements Runnable {
        C14901() {
        }

        public void run() {
            HashMap<Long, Boolean> typings = (HashMap) MessagesController$56.this.this$0.sendingTypings.get(Integer.valueOf(MessagesController$56.this.val$action));
            if (typings != null) {
                typings.remove(Long.valueOf(MessagesController$56.this.val$dialog_id));
            }
        }
    }

    MessagesController$56(MessagesController this$0, int i, long j) {
        this.this$0 = this$0;
        this.val$action = i;
        this.val$dialog_id = j;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new C14901());
    }
}
