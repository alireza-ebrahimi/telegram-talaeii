package org.telegram.tgnet;

import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;

class ConnectionsManager$8 implements Runnable {
    ConnectionsManager$8() {
    }

    public void run() {
        if (UserConfig.getClientUserId() != 0) {
            UserConfig.clearConfig();
            MessagesController.getInstance().performLogout(false);
        }
    }
}
