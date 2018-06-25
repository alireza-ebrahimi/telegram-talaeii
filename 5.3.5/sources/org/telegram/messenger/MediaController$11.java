package org.telegram.messenger;

class MediaController$11 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ MessageObject val$currentMessageObject;

    MediaController$11(MediaController this$0, MessageObject messageObject) {
        this.this$0 = this$0;
        this.val$currentMessageObject = messageObject;
    }

    public void run() {
        this.this$0.pauseMessage(this.val$currentMessageObject);
    }
}
