package org.telegram.messenger;

import org.telegram.tgnet.TLRPC$Chat;

class MessagesController$10 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$Chat val$chat;

    MessagesController$10(MessagesController this$0, TLRPC$Chat tLRPC$Chat) {
        this.this$0 = this$0;
        this.val$chat = tLRPC$Chat;
    }

    public void run() {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.channelRightsUpdated, new Object[]{this.val$chat});
    }
}
