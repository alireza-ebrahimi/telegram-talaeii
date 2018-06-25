package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$TL_webPageEmpty;
import org.telegram.tgnet.TLRPC$messages_Messages;

class MessagesController$59 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ String val$url;

    MessagesController$59(MessagesController this$0, String str, long j) {
        this.this$0 = this$0;
        this.val$url = str;
        this.val$dialog_id = j;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                ArrayList<MessageObject> arrayList = (ArrayList) MessagesController.access$4600(MessagesController$59.this.this$0).remove(MessagesController$59.this.val$url);
                if (arrayList != null) {
                    TLRPC$messages_Messages messagesRes = new TLRPC$TL_messages_messages();
                    int a;
                    if (response instanceof TLRPC$TL_messageMediaWebPage) {
                        TLRPC$TL_messageMediaWebPage media = response;
                        if ((media.webpage instanceof TLRPC$TL_webPage) || (media.webpage instanceof TLRPC$TL_webPageEmpty)) {
                            for (a = 0; a < arrayList.size(); a++) {
                                ((MessageObject) arrayList.get(a)).messageOwner.media.webpage = media.webpage;
                                if (a == 0) {
                                    ImageLoader.saveMessageThumbs(((MessageObject) arrayList.get(a)).messageOwner);
                                }
                                messagesRes.messages.add(((MessageObject) arrayList.get(a)).messageOwner);
                            }
                        } else {
                            MessagesController.access$4700(MessagesController$59.this.this$0).put(Long.valueOf(media.webpage.id), arrayList);
                        }
                    } else {
                        for (a = 0; a < arrayList.size(); a++) {
                            ((MessageObject) arrayList.get(a)).messageOwner.media.webpage = new TLRPC$TL_webPageEmpty();
                            messagesRes.messages.add(((MessageObject) arrayList.get(a)).messageOwner);
                        }
                    }
                    if (!messagesRes.messages.isEmpty()) {
                        MessagesStorage.getInstance().putMessages(messagesRes, MessagesController$59.this.val$dialog_id, -2, 0, false);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.replaceMessagesObjects, new Object[]{Long.valueOf(MessagesController$59.this.val$dialog_id), arrayList});
                    }
                }
            }
        });
    }
}
