package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

class MediaController$23 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ String val$id;
    final /* synthetic */ String val$path;

    MediaController$23(MediaController this$0, String str, String str2) {
        this.this$0 = this$0;
        this.val$path = str;
        this.val$id = str2;
    }

    public void run() {
        final byte[] waveform = MediaController.getInstance().getWaveform(this.val$path);
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                MessageObject messageObject = (MessageObject) MediaController.access$7100(MediaController$23.this.this$0).remove(MediaController$23.this.val$id);
                if (messageObject != null && waveform != null) {
                    for (int a = 0; a < messageObject.getDocument().attributes.size(); a++) {
                        DocumentAttribute attribute = (DocumentAttribute) messageObject.getDocument().attributes.get(a);
                        if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                            attribute.waveform = waveform;
                            attribute.flags |= 4;
                            break;
                        }
                    }
                    TLRPC$messages_Messages messagesRes = new TLRPC$TL_messages_messages();
                    messagesRes.messages.add(messageObject.messageOwner);
                    MessagesStorage.getInstance().putMessages(messagesRes, messageObject.getDialogId(), -1, 0, false);
                    new ArrayList().add(messageObject);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.replaceMessagesObjects, new Object[]{Long.valueOf(messageObject.getDialogId()), arrayList});
                }
            }
        });
    }
}
