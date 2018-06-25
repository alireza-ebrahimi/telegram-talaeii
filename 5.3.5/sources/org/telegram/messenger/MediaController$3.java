package org.telegram.messenger;

class MediaController$3 implements Runnable {
    final /* synthetic */ MediaController this$0;

    MediaController$3(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.FileDidFailedLoad);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.didReceivedNewMessages);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.messagesDeleted);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.FileLoadProgressChanged);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.FileUploadProgressChanged);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.removeAllMessagesFromDialog);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.musicDidLoaded);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.httpFileDidLoaded);
        NotificationCenter.getInstance().addObserver(this.this$0, NotificationCenter.httpFileDidFailedLoad);
    }
}
