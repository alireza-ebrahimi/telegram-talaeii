package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$messages_Dialogs;

class MessagesController$64 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$date;
    final /* synthetic */ int val$newPts;
    final /* synthetic */ int val$qts;
    final /* synthetic */ int val$seq;

    MessagesController$64(MessagesController this$0, int i, int i2, int i3, int i4) {
        this.this$0 = this$0;
        this.val$seq = i;
        this.val$newPts = i2;
        this.val$date = i3;
        this.val$qts = i4;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            MessagesController.access$1202(this.this$0, (TLRPC$messages_Dialogs) response);
            MessagesController.access$5000(this.this$0, false, this.val$seq, this.val$newPts, this.val$date, this.val$qts);
        }
    }
}
