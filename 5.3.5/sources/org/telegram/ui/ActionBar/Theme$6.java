package org.telegram.ui.ActionBar;

import org.telegram.messenger.NotificationCenter;

class Theme$6 implements Runnable {
    Theme$6() {
    }

    public void run() {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetNewTheme, new Object[0]);
    }
}
