package org.telegram.messenger;

import org.telegram.tgnet.TLRPC$photos_Photos;

class MessagesController$37 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$classGuid;
    final /* synthetic */ int val$count;
    final /* synthetic */ int val$did;
    final /* synthetic */ boolean val$fromCache;
    final /* synthetic */ TLRPC$photos_Photos val$res;

    MessagesController$37(MessagesController this$0, TLRPC$photos_Photos tLRPC$photos_Photos, boolean z, int i, int i2, int i3) {
        this.this$0 = this$0;
        this.val$res = tLRPC$photos_Photos;
        this.val$fromCache = z;
        this.val$did = i;
        this.val$count = i2;
        this.val$classGuid = i3;
    }

    public void run() {
        this.this$0.putUsers(this.val$res.users, this.val$fromCache);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogPhotosLoaded, new Object[]{Integer.valueOf(this.val$did), Integer.valueOf(this.val$count), Boolean.valueOf(this.val$fromCache), Integer.valueOf(this.val$classGuid), this.val$res.photos});
    }
}
