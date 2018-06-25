package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$34 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ boolean val$cache;
    final /* synthetic */ ArrayList val$ids;
    final /* synthetic */ ArrayList val$users;

    MessagesController$34(MessagesController this$0, ArrayList arrayList, boolean z, ArrayList arrayList2) {
        this.this$0 = this$0;
        this.val$users = arrayList;
        this.val$cache = z;
        this.val$ids = arrayList2;
    }

    public void run() {
        if (this.val$users != null) {
            this.this$0.putUsers(this.val$users, this.val$cache);
        }
        this.this$0.loadingBlockedUsers = false;
        if (this.val$ids.isEmpty() && this.val$cache && !UserConfig.blockedUsersLoaded) {
            this.this$0.getBlockedUsers(false);
            return;
        }
        if (!this.val$cache) {
            UserConfig.blockedUsersLoaded = true;
            UserConfig.saveConfig(false);
        }
        this.this$0.blockedUsers = this.val$ids;
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.blockedUsersDidLoaded, new Object[0]);
    }
}
