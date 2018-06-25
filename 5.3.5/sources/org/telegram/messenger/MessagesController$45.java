package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_affectedHistory;

class MessagesController$45 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$did;
    final /* synthetic */ int val$max_id_delete_final;
    final /* synthetic */ int val$onlyHistory;

    MessagesController$45(MessagesController this$0, long j, int i, int i2) {
        this.this$0 = this$0;
        this.val$did = j;
        this.val$onlyHistory = i;
        this.val$max_id_delete_final = i2;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            TLRPC$TL_messages_affectedHistory res = (TLRPC$TL_messages_affectedHistory) response;
            if (res.offset > 0) {
                MessagesController.access$3900(this.this$0, this.val$did, false, this.val$onlyHistory, this.val$max_id_delete_final);
            }
            this.this$0.processNewDifferenceParams(-1, res.pts, -1, res.pts_count);
        }
    }
}
