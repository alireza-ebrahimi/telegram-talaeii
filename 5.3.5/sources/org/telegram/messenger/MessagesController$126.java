package org.telegram.messenger;

import org.telegram.tgnet.TLRPC$TL_updateUserBlocked;

class MessagesController$126 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$TL_updateUserBlocked val$finalUpdate;

    /* renamed from: org.telegram.messenger.MessagesController$126$1 */
    class C14621 implements Runnable {
        C14621() {
        }

        public void run() {
            if (!MessagesController$126.this.val$finalUpdate.blocked) {
                MessagesController$126.this.this$0.blockedUsers.remove(Integer.valueOf(MessagesController$126.this.val$finalUpdate.user_id));
            } else if (!MessagesController$126.this.this$0.blockedUsers.contains(Integer.valueOf(MessagesController$126.this.val$finalUpdate.user_id))) {
                MessagesController$126.this.this$0.blockedUsers.add(Integer.valueOf(MessagesController$126.this.val$finalUpdate.user_id));
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.blockedUsersDidLoaded, new Object[0]);
        }
    }

    MessagesController$126(MessagesController this$0, TLRPC$TL_updateUserBlocked tLRPC$TL_updateUserBlocked) {
        this.this$0 = this$0;
        this.val$finalUpdate = tLRPC$TL_updateUserBlocked;
    }

    public void run() {
        AndroidUtilities.runOnUIThread(new C14621());
    }
}
