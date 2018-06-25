package org.telegram.tgnet;

import org.telegram.messenger.NotificationCenter;

class ConnectionsManager$7 implements Runnable {
    final /* synthetic */ int val$state;

    ConnectionsManager$7(int i) {
        this.val$state = i;
    }

    public void run() {
        ConnectionsManager.access$102(ConnectionsManager.getInstance(), this.val$state);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedConnectionState, new Object[0]);
    }
}
