package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Updates;

class MessagesController$94 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$chat_id;

    MessagesController$94(MessagesController this$0, int i) {
        this.this$0 = this$0;
        this.val$chat_id = i;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            this.this$0.processUpdates((TLRPC$Updates) response, false);
            this.this$0.loadFullChat(this.val$chat_id, 0, true);
        }
    }
}
