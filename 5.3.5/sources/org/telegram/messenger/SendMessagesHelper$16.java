package org.telegram.messenger;

import java.util.HashMap;
import org.telegram.tgnet.TLRPC$TL_document;

class SendMessagesHelper$16 implements Runnable {
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ TLRPC$TL_document val$documentFinal;
    final /* synthetic */ HashMap val$params;
    final /* synthetic */ String val$pathFinal;
    final /* synthetic */ MessageObject val$reply_to_msg;

    SendMessagesHelper$16(TLRPC$TL_document tLRPC$TL_document, String str, long j, MessageObject messageObject, HashMap hashMap) {
        this.val$documentFinal = tLRPC$TL_document;
        this.val$pathFinal = str;
        this.val$dialog_id = j;
        this.val$reply_to_msg = messageObject;
        this.val$params = hashMap;
    }

    public void run() {
        SendMessagesHelper.getInstance().sendMessage(this.val$documentFinal, null, this.val$pathFinal, this.val$dialog_id, this.val$reply_to_msg, null, this.val$params, 0);
    }
}
