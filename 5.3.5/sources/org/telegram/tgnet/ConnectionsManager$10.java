package org.telegram.tgnet;

import org.telegram.messenger.FileLog;

class ConnectionsManager$10 implements Runnable {
    ConnectionsManager$10() {
    }

    public void run() {
        try {
            if (!ConnectionsManager.access$000(ConnectionsManager.getInstance()).isHeld()) {
                ConnectionsManager.access$000(ConnectionsManager.getInstance()).acquire(10000);
                FileLog.d("acquire wakelock");
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }
}
