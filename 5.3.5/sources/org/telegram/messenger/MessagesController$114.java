package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;

class MessagesController$114 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$newTaskId;

    MessagesController$114(MessagesController this$0, long j) {
        this.this$0 = this$0;
        this.val$newTaskId = j;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (this.val$newTaskId != 0) {
            MessagesStorage.getInstance().removePendingTask(this.val$newTaskId);
        }
    }
}
