package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$Message;

class SendMessagesHelper$15 implements Runnable {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ ArrayList val$chats;
    final /* synthetic */ ArrayList val$encryptedChats;
    final /* synthetic */ ArrayList val$messages;
    final /* synthetic */ ArrayList val$users;

    SendMessagesHelper$15(SendMessagesHelper this$0, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
        this.this$0 = this$0;
        this.val$users = arrayList;
        this.val$chats = arrayList2;
        this.val$encryptedChats = arrayList3;
        this.val$messages = arrayList4;
    }

    public void run() {
        MessagesController.getInstance().putUsers(this.val$users, true);
        MessagesController.getInstance().putChats(this.val$chats, true);
        MessagesController.getInstance().putEncryptedChats(this.val$encryptedChats, true);
        for (int a = 0; a < this.val$messages.size(); a++) {
            this.this$0.retrySendMessage(new MessageObject((TLRPC$Message) this.val$messages.get(a), null, false), true);
        }
    }
}
