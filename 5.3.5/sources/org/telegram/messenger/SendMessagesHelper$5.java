package org.telegram.messenger;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$TL_document;

class SendMessagesHelper$5 implements OnClickListener {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ TLRPC$Document val$finalDocument1;
    final /* synthetic */ long val$peer;
    final /* synthetic */ MessageObject val$replyingMessageObject;

    SendMessagesHelper$5(SendMessagesHelper this$0, TLRPC$Document tLRPC$Document, long j, MessageObject messageObject) {
        this.this$0 = this$0;
        this.val$finalDocument1 = tLRPC$Document;
        this.val$peer = j;
        this.val$replyingMessageObject = messageObject;
    }

    public void onClick(DialogInterface dialog, int id) {
        SendMessagesHelper.getInstance().sendMessage((TLRPC$TL_document) this.val$finalDocument1, null, null, this.val$peer, this.val$replyingMessageObject, null, null, 0);
    }
}
