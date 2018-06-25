package org.telegram.messenger;

import java.io.File;
import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_photo;

class SendMessagesHelper$2 implements Runnable {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ File val$cacheFile;
    final /* synthetic */ SendMessagesHelper$DelayedMessage val$message;
    final /* synthetic */ MessageObject val$messageObject;
    final /* synthetic */ String val$path;

    SendMessagesHelper$2(SendMessagesHelper this$0, File file, MessageObject messageObject, SendMessagesHelper$DelayedMessage sendMessagesHelper$DelayedMessage, String str) {
        this.this$0 = this$0;
        this.val$cacheFile = file;
        this.val$messageObject = messageObject;
        this.val$message = sendMessagesHelper$DelayedMessage;
        this.val$path = str;
    }

    public void run() {
        final TLRPC$TL_photo photo = SendMessagesHelper.getInstance().generatePhotoSizes(this.val$cacheFile.toString(), null);
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (photo != null) {
                    SendMessagesHelper$2.this.val$messageObject.messageOwner.media.photo = photo;
                    SendMessagesHelper$2.this.val$messageObject.messageOwner.attachPath = SendMessagesHelper$2.this.val$cacheFile.toString();
                    ArrayList messages = new ArrayList();
                    messages.add(SendMessagesHelper$2.this.val$messageObject.messageOwner);
                    MessagesStorage.getInstance().putMessages(messages, false, true, false, 0);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMessageMedia, new Object[]{SendMessagesHelper$2.this.val$messageObject.messageOwner});
                    SendMessagesHelper$2.this.val$message.location = ((TLRPC$PhotoSize) photo.sizes.get(photo.sizes.size() - 1)).location;
                    SendMessagesHelper$2.this.val$message.httpLocation = null;
                    if (SendMessagesHelper$2.this.val$message.type == 4) {
                        SendMessagesHelper.access$1000(SendMessagesHelper$2.this.this$0, SendMessagesHelper$2.this.val$message, SendMessagesHelper$2.this.val$message.messageObjects.indexOf(SendMessagesHelper$2.this.val$messageObject));
                        return;
                    } else {
                        SendMessagesHelper.access$1100(SendMessagesHelper$2.this.this$0, SendMessagesHelper$2.this.val$message);
                        return;
                    }
                }
                FileLog.e("can't load image " + SendMessagesHelper$2.this.val$path + " to file " + SendMessagesHelper$2.this.val$cacheFile.toString());
                SendMessagesHelper$2.this.val$message.markAsError();
            }
        });
    }
}
