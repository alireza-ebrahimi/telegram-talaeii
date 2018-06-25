package org.telegram.tgnet;

import org.telegram.messenger.MessagesController;

class ConnectionsManager$5 implements Runnable {
    ConnectionsManager$5() {
    }

    public void run() {
        MessagesController.getInstance().updateTimerProc();
    }
}
