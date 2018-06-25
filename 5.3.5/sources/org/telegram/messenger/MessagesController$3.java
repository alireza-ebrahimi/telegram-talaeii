package org.telegram.messenger;

class MessagesController$3 implements Runnable {
    final /* synthetic */ MessagesController this$0;

    MessagesController$3(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        MessagesController messagesController = MessagesController.getInstance();
        NotificationCenter.getInstance().addObserver(messagesController, NotificationCenter.FileDidUpload);
        NotificationCenter.getInstance().addObserver(messagesController, NotificationCenter.FileDidFailUpload);
        NotificationCenter.getInstance().addObserver(messagesController, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().addObserver(messagesController, NotificationCenter.FileDidFailedLoad);
        NotificationCenter.getInstance().addObserver(messagesController, NotificationCenter.messageReceivedByServer);
        NotificationCenter.getInstance().addObserver(messagesController, NotificationCenter.updateMessageMedia);
    }
}
