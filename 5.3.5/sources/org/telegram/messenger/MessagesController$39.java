package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_affectedMessages;

class MessagesController$39 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$newTaskId;

    MessagesController$39(MessagesController this$0, long j) {
        this.this$0 = this$0;
        this.val$newTaskId = j;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            TLRPC$TL_messages_affectedMessages res = (TLRPC$TL_messages_affectedMessages) response;
            this.this$0.processNewDifferenceParams(-1, res.pts, -1, res.pts_count);
        }
        if (this.val$newTaskId != 0) {
            MessagesStorage.getInstance().removePendingTask(this.val$newTaskId);
        }
    }
}
