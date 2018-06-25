package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Updates;

class SendMessagesHelper$9 implements RequestDelegate {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ long val$newTaskId;

    SendMessagesHelper$9(SendMessagesHelper this$0, long j) {
        this.this$0 = this$0;
        this.val$newTaskId = j;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            MessagesController.getInstance().processUpdates((TLRPC$Updates) response, false);
        }
        if (this.val$newTaskId != 0) {
            MessagesStorage.getInstance().removePendingTask(this.val$newTaskId);
        }
    }
}
