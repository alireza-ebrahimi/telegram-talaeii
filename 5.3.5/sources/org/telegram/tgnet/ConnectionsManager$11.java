package org.telegram.tgnet;

import org.telegram.messenger.NotificationCenter;

class ConnectionsManager$11 implements Runnable {
    final /* synthetic */ ConnectionsManager this$0;
    final /* synthetic */ boolean val$value;

    ConnectionsManager$11(ConnectionsManager this$0, boolean z) {
        this.this$0 = this$0;
        this.val$value = z;
    }

    public void run() {
        if (ConnectionsManager.access$400(this.this$0) != this.val$value) {
            ConnectionsManager.access$402(this.this$0, this.val$value);
            if (ConnectionsManager.access$100(this.this$0) == 3) {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didUpdatedConnectionState, new Object[0]);
            }
        }
    }
}
