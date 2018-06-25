package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$photos_Photos;

class MessagesController$27 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$classGuid;
    final /* synthetic */ int val$count;
    final /* synthetic */ int val$did;
    final /* synthetic */ long val$max_id;

    MessagesController$27(MessagesController this$0, int i, int i2, long j, int i3) {
        this.this$0 = this$0;
        this.val$did = i;
        this.val$count = i2;
        this.val$max_id = j;
        this.val$classGuid = i3;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            this.this$0.processLoadedUserPhotos((TLRPC$photos_Photos) response, this.val$did, this.val$count, this.val$max_id, false, this.val$classGuid);
        }
    }
}
