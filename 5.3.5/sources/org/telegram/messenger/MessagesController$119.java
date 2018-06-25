package org.telegram.messenger;

import java.util.ArrayList;

class MessagesController$119 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$objArr;
    final /* synthetic */ boolean val$printUpdate;
    final /* synthetic */ int val$user_id;

    MessagesController$119(MessagesController this$0, boolean z, int i, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$printUpdate = z;
        this.val$user_id = i;
        this.val$objArr = arrayList;
    }

    public void run() {
        if (this.val$printUpdate) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(64)});
        }
        this.this$0.updateInterfaceWithMessages((long) this.val$user_id, this.val$objArr);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
    }
}
