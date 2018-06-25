package org.telegram.messenger;

import java.io.File;

class MediaController$30 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ boolean val$error;
    final /* synthetic */ File val$file;
    final /* synthetic */ boolean val$firstWrite;
    final /* synthetic */ boolean val$last;
    final /* synthetic */ MessageObject val$messageObject;

    MediaController$30(MediaController this$0, boolean z, boolean z2, MessageObject messageObject, File file, boolean z3) {
        this.this$0 = this$0;
        this.val$error = z;
        this.val$last = z2;
        this.val$messageObject = messageObject;
        this.val$file = file;
        this.val$firstWrite = z3;
    }

    public void run() {
        if (this.val$error || this.val$last) {
            synchronized (MediaController.access$7700(this.this$0)) {
                MediaController.access$7802(this.this$0, false);
            }
            MediaController.access$7900(this.this$0).remove(this.val$messageObject);
            MediaController.access$8000(this.this$0);
        }
        if (this.val$error) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.FilePreparingFailed, new Object[]{this.val$messageObject, this.val$file.toString()});
            return;
        }
        if (this.val$firstWrite) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.FilePreparingStarted, new Object[]{this.val$messageObject, this.val$file.toString()});
        }
        NotificationCenter instance = NotificationCenter.getInstance();
        int i = NotificationCenter.FileNewChunkAvailable;
        Object[] objArr = new Object[3];
        objArr[0] = this.val$messageObject;
        objArr[1] = this.val$file.toString();
        objArr[2] = Long.valueOf(this.val$last ? this.val$file.length() : 0);
        instance.postNotificationName(i, objArr);
    }
}
