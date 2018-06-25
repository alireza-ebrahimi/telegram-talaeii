package org.telegram.tgnet;

import org.telegram.messenger.MessagesController;

class ConnectionsManager$4 implements Runnable {
    final /* synthetic */ TLObject val$message;

    ConnectionsManager$4(TLObject tLObject) {
        this.val$message = tLObject;
    }

    public void run() {
        MessagesController.getInstance().processUpdates((TLRPC$Updates) this.val$message, false);
    }
}
