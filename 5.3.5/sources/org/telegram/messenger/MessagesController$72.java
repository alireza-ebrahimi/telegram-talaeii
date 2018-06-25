package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$Message;

class MessagesController$72 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$Message val$message;

    MessagesController$72(MessagesController this$0, TLRPC$Message tLRPC$Message) {
        this.this$0 = this$0;
        this.val$message = tLRPC$Message;
    }

    public void run() {
        int peer;
        if (this.val$message.to_id.channel_id != 0) {
            peer = -this.val$message.to_id.channel_id;
        } else if (this.val$message.to_id.chat_id != 0) {
            peer = -this.val$message.to_id.chat_id;
        } else {
            peer = this.val$message.to_id.user_id;
        }
        ArrayList<Integer> ids = (ArrayList) MessagesController.access$5800(this.this$0).get(peer);
        if (ids == null) {
            ids = new ArrayList();
            MessagesController.access$5800(this.this$0).put(peer, ids);
        }
        if (!ids.contains(Integer.valueOf(this.val$message.id))) {
            ids.add(Integer.valueOf(this.val$message.id));
        }
    }
}
