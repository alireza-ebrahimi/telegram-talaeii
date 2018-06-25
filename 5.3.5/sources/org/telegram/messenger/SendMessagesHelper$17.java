package org.telegram.messenger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.TLRPC$TL_document;

class SendMessagesHelper$17 implements Runnable {
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ ArrayList val$messageObjects;
    final /* synthetic */ MessageObject val$reply_to_msg;

    SendMessagesHelper$17(ArrayList arrayList, long j, MessageObject messageObject) {
        this.val$messageObjects = arrayList;
        this.val$dialog_id = j;
        this.val$reply_to_msg = messageObject;
    }

    public void run() {
        int size = this.val$messageObjects.size();
        for (int a = 0; a < size; a++) {
            final MessageObject messageObject = (MessageObject) this.val$messageObjects.get(a);
            String originalPath = messageObject.messageOwner.attachPath;
            File f = new File(originalPath);
            boolean isEncrypted = ((int) this.val$dialog_id) == 0;
            if (originalPath != null) {
                originalPath = originalPath + MimeTypes.BASE_TYPE_AUDIO + f.length();
            }
            TLRPC$TL_document tLRPC$TL_document = null;
            if (!isEncrypted) {
                tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(originalPath, !isEncrypted ? 1 : 4);
            }
            if (tLRPC$TL_document == null) {
                tLRPC$TL_document = messageObject.messageOwner.media.document;
            }
            if (isEncrypted) {
                if (MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (this.val$dialog_id >> 32))) == null) {
                    return;
                }
            }
            final HashMap<String, String> params = new HashMap();
            if (originalPath != null) {
                params.put("originalPath", originalPath);
            }
            final TLRPC$TL_document documentFinal = tLRPC$TL_document;
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    SendMessagesHelper.getInstance().sendMessage(documentFinal, null, messageObject.messageOwner.attachPath, SendMessagesHelper$17.this.val$dialog_id, SendMessagesHelper$17.this.val$reply_to_msg, null, params, 0);
                }
            });
        }
    }
}
