package org.telegram.messenger;

import android.graphics.Bitmap;
import java.io.File;
import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;

class SendMessagesHelper$3 implements Runnable {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ File val$cacheFile;
    final /* synthetic */ SendMessagesHelper$DelayedMessage val$message;
    final /* synthetic */ MessageObject val$messageObject;

    SendMessagesHelper$3(SendMessagesHelper this$0, SendMessagesHelper$DelayedMessage sendMessagesHelper$DelayedMessage, File file, MessageObject messageObject) {
        this.this$0 = this$0;
        this.val$message = sendMessagesHelper$DelayedMessage;
        this.val$cacheFile = file;
        this.val$messageObject = messageObject;
    }

    public void run() {
        boolean z = true;
        final TLRPC$Document document = this.val$message.obj.getDocument();
        if (document.thumb.location instanceof TLRPC$TL_fileLocationUnavailable) {
            try {
                Bitmap bitmap = ImageLoader.loadBitmap(this.val$cacheFile.getAbsolutePath(), null, 90.0f, 90.0f, true);
                if (bitmap != null) {
                    if (this.val$message.sendEncryptedRequest == null) {
                        z = false;
                    }
                    document.thumb = ImageLoader.scaleAndSaveImage(bitmap, 90.0f, 90.0f, 55, z);
                    bitmap.recycle();
                }
            } catch (Exception e) {
                document.thumb = null;
                FileLog.e(e);
            }
            if (document.thumb == null) {
                document.thumb = new TLRPC$TL_photoSizeEmpty();
                document.thumb.type = "s";
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                SendMessagesHelper$3.this.val$message.httpLocation = null;
                SendMessagesHelper$3.this.val$message.obj.messageOwner.attachPath = SendMessagesHelper$3.this.val$cacheFile.toString();
                SendMessagesHelper$3.this.val$message.location = document.thumb.location;
                ArrayList messages = new ArrayList();
                messages.add(SendMessagesHelper$3.this.val$messageObject.messageOwner);
                MessagesStorage.getInstance().putMessages(messages, false, true, false, 0);
                SendMessagesHelper.access$1100(SendMessagesHelper$3.this.this$0, SendMessagesHelper$3.this.val$message);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateMessageMedia, new Object[]{SendMessagesHelper$3.this.val$message.obj.messageOwner});
            }
        });
    }
}
