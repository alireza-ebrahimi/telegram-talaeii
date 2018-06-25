package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_affectedHistory;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$41 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$Chat val$chat;
    final /* synthetic */ User val$user;

    MessagesController$41(MessagesController this$0, TLRPC$Chat tLRPC$Chat, User user) {
        this.this$0 = this$0;
        this.val$chat = tLRPC$Chat;
        this.val$user = user;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            TLRPC$TL_messages_affectedHistory res = (TLRPC$TL_messages_affectedHistory) response;
            if (res.offset > 0) {
                this.this$0.deleteUserChannelHistory(this.val$chat, this.val$user, res.offset);
            }
            this.this$0.processNewChannelDifferenceParams(res.pts, res.pts_count, this.val$chat.id);
        }
    }
}
