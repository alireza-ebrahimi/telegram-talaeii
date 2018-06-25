package org.telegram.tgnet;

import org.telegram.messenger.MessagesController;

class ConnectionsManager$6 implements Runnable {
    ConnectionsManager$6() {
    }

    public void run() {
        MessagesController.getInstance().getDifference();
    }
}
