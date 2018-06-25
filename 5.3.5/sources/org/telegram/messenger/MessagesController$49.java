package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.TLRPC$ChatFull;

class MessagesController$49 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ boolean val$byChannelUsers;
    final /* synthetic */ boolean val$fromCache;
    final /* synthetic */ TLRPC$ChatFull val$info;
    final /* synthetic */ MessageObject val$pinnedMessageObject;
    final /* synthetic */ ArrayList val$usersArr;

    MessagesController$49(MessagesController this$0, ArrayList arrayList, boolean z, TLRPC$ChatFull tLRPC$ChatFull, boolean z2, MessageObject messageObject) {
        this.this$0 = this$0;
        this.val$usersArr = arrayList;
        this.val$fromCache = z;
        this.val$info = tLRPC$ChatFull;
        this.val$byChannelUsers = z2;
        this.val$pinnedMessageObject = messageObject;
    }

    public void run() {
        this.this$0.putUsers(this.val$usersArr, this.val$fromCache);
        if (this.val$info.stickerset != null) {
            StickersQuery.getGroupStickerSetById(this.val$info.stickerset);
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{this.val$info, Integer.valueOf(0), Boolean.valueOf(this.val$byChannelUsers), this.val$pinnedMessageObject});
    }
}
