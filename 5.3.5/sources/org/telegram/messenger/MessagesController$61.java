package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_help_recentMeUrls;

class MessagesController$61 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    MessagesController$61(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(final TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    TLRPC$TL_help_recentMeUrls res = response;
                    MessagesController$61.this.this$0.putUsers(res.users, false);
                    MessagesController$61.this.this$0.putChats(res.chats, false);
                    MessagesController$61.this.this$0.hintDialogs.clear();
                    MessagesController$61.this.this$0.hintDialogs.addAll(res.urls);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                }
            });
        }
    }
}
