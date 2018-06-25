package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_updates_state;

class MessagesController$105 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    MessagesController$105(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        this.this$0.updatingState = false;
        if (error == null) {
            TLRPC$TL_updates_state res = (TLRPC$TL_updates_state) response;
            MessagesStorage.lastDateValue = res.date;
            MessagesStorage.lastPtsValue = res.pts;
            MessagesStorage.lastSeqValue = res.seq;
            MessagesStorage.lastQtsValue = res.qts;
            for (int a = 0; a < 3; a++) {
                MessagesController.access$6000(this.this$0, a, 2);
            }
            MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
        } else if (error.code != 401) {
            this.this$0.loadCurrentState();
        }
    }
}
