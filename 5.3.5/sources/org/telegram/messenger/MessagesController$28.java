package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_photos_photos;
import org.telegram.tgnet.TLRPC$messages_Messages;

class MessagesController$28 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$classGuid;
    final /* synthetic */ int val$count;
    final /* synthetic */ int val$did;
    final /* synthetic */ long val$max_id;

    MessagesController$28(MessagesController this$0, int i, int i2, long j, int i3) {
        this.this$0 = this$0;
        this.val$did = i;
        this.val$count = i2;
        this.val$max_id = j;
        this.val$classGuid = i3;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            TLRPC$messages_Messages messages = (TLRPC$messages_Messages) response;
            TLRPC$TL_photos_photos res = new TLRPC$TL_photos_photos();
            res.count = messages.count;
            res.users.addAll(messages.users);
            for (int a = 0; a < messages.messages.size(); a++) {
                TLRPC$Message message = (TLRPC$Message) messages.messages.get(a);
                if (!(message.action == null || message.action.photo == null)) {
                    res.photos.add(message.action.photo);
                }
            }
            this.this$0.processLoadedUserPhotos(res, this.val$did, this.val$count, this.val$max_id, false, this.val$classGuid);
        }
    }
}
