package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Updates;

class MessagesController$88 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    MessagesController$88(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            this.this$0.processUpdates((TLRPC$Updates) response, false);
        }
    }
}
