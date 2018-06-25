package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$InputMedia;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputDocument;
import org.telegram.tgnet.TLRPC$TL_inputMediaDocument;
import org.telegram.tgnet.TLRPC$TL_inputMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_inputMediaUploadedDocument;
import org.telegram.tgnet.TLRPC$TL_inputMediaUploadedPhoto;
import org.telegram.tgnet.TLRPC$TL_inputPhoto;
import org.telegram.tgnet.TLRPC$TL_inputSingleMedia;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messages_sendMultiMedia;

class SendMessagesHelper$10 implements RequestDelegate {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ TLRPC$InputMedia val$inputMedia;
    final /* synthetic */ SendMessagesHelper$DelayedMessage val$message;

    SendMessagesHelper$10(SendMessagesHelper this$0, TLRPC$InputMedia tLRPC$InputMedia, SendMessagesHelper$DelayedMessage sendMessagesHelper$DelayedMessage) {
        this.this$0 = this$0;
        this.val$inputMedia = tLRPC$InputMedia;
        this.val$message = sendMessagesHelper$DelayedMessage;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                TLRPC$InputMedia newInputMedia = null;
                if (response != null) {
                    TLRPC$MessageMedia messageMedia = response;
                    if ((SendMessagesHelper$10.this.val$inputMedia instanceof TLRPC$TL_inputMediaUploadedPhoto) && (messageMedia instanceof TLRPC$TL_messageMediaPhoto)) {
                        TLRPC$InputMedia inputMediaPhoto = new TLRPC$TL_inputMediaPhoto();
                        inputMediaPhoto.id = new TLRPC$TL_inputPhoto();
                        inputMediaPhoto.id.id = messageMedia.photo.id;
                        inputMediaPhoto.id.access_hash = messageMedia.photo.access_hash;
                        newInputMedia = inputMediaPhoto;
                    } else if ((SendMessagesHelper$10.this.val$inputMedia instanceof TLRPC$TL_inputMediaUploadedDocument) && (messageMedia instanceof TLRPC$TL_messageMediaDocument)) {
                        TLRPC$InputMedia inputMediaDocument = new TLRPC$TL_inputMediaDocument();
                        inputMediaDocument.id = new TLRPC$TL_inputDocument();
                        inputMediaDocument.id.id = messageMedia.document.id;
                        inputMediaDocument.id.access_hash = messageMedia.document.access_hash;
                        newInputMedia = inputMediaDocument;
                    }
                }
                if (newInputMedia != null) {
                    newInputMedia.caption = SendMessagesHelper$10.this.val$inputMedia.caption;
                    if (SendMessagesHelper$10.this.val$inputMedia.ttl_seconds != 0) {
                        newInputMedia.ttl_seconds = SendMessagesHelper$10.this.val$inputMedia.ttl_seconds;
                        newInputMedia.flags |= 1;
                    }
                    TLRPC$TL_messages_sendMultiMedia req = SendMessagesHelper$10.this.val$message.sendRequest;
                    for (int a = 0; a < req.multi_media.size(); a++) {
                        if (((TLRPC$TL_inputSingleMedia) req.multi_media.get(a)).media == SendMessagesHelper$10.this.val$inputMedia) {
                            ((TLRPC$TL_inputSingleMedia) req.multi_media.get(a)).media = newInputMedia;
                            break;
                        }
                    }
                    SendMessagesHelper.access$1400(SendMessagesHelper$10.this.this$0, SendMessagesHelper$10.this.val$message, false, true);
                    return;
                }
                SendMessagesHelper$10.this.val$message.markAsError();
            }
        });
    }
}
