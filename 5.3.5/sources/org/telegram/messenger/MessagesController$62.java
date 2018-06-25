package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$messages_Dialogs;

class MessagesController$62 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$count;

    MessagesController$62(MessagesController this$0, int i) {
        this.this$0 = this$0;
        this.val$count = i;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            this.this$0.processLoadedDialogs((TLRPC$messages_Dialogs) response, null, 0, this.val$count, 0, false, false, false);
        }
    }
}
