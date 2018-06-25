package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$Updates;

class MessagesController$120 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$objArr;
    final /* synthetic */ boolean val$printUpdate;
    final /* synthetic */ TLRPC$Updates val$updates;

    MessagesController$120(MessagesController this$0, boolean z, TLRPC$Updates tLRPC$Updates, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$printUpdate = z;
        this.val$updates = tLRPC$Updates;
        this.val$objArr = arrayList;
    }

    public void run() {
        if (this.val$printUpdate) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(64)});
        }
        this.this$0.updateInterfaceWithMessages((long) (-this.val$updates.chat_id), this.val$objArr);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
    }
}
