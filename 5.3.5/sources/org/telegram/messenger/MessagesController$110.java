package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_dialogs;
import org.telegram.tgnet.TLRPC$TL_messages_peerDialogs;

class MessagesController$110 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$Chat val$channel;
    final /* synthetic */ long val$newTaskId;

    MessagesController$110(MessagesController this$0, long j, TLRPC$Chat tLRPC$Chat) {
        this.this$0 = this$0;
        this.val$newTaskId = j;
        this.val$channel = tLRPC$Chat;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response != null) {
            TLRPC$TL_messages_peerDialogs res = (TLRPC$TL_messages_peerDialogs) response;
            if (!(res.dialogs.isEmpty() || res.chats.isEmpty())) {
                TLRPC$TL_messages_dialogs dialogs = new TLRPC$TL_messages_dialogs();
                dialogs.dialogs.addAll(res.dialogs);
                dialogs.messages.addAll(res.messages);
                dialogs.users.addAll(res.users);
                dialogs.chats.addAll(res.chats);
                this.this$0.processLoadedDialogs(dialogs, null, 0, 1, 2, false, false, false);
            }
        }
        if (this.val$newTaskId != 0) {
            MessagesStorage.getInstance().removePendingTask(this.val$newTaskId);
        }
        MessagesController.access$600(this.this$0).remove(Integer.valueOf(this.val$channel.id));
    }
}
