package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$32 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ User val$user;

    MessagesController$32(MessagesController this$0, User user) {
        this.this$0 = this$0;
        this.val$user = user;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        MessagesStorage.getInstance().deleteBlockedUser(this.val$user.id);
    }
}
