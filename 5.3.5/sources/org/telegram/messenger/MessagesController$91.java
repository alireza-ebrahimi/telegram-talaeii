package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;

class MessagesController$91 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ String val$about;
    final /* synthetic */ TLRPC$ChatFull val$info;

    /* renamed from: org.telegram.messenger.MessagesController$91$1 */
    class C15181 implements Runnable {
        C15181() {
        }

        public void run() {
            MessagesController$91.this.val$info.about = MessagesController$91.this.val$about;
            MessagesStorage.getInstance().updateChatInfo(MessagesController$91.this.val$info, false);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, new Object[]{MessagesController$91.this.val$info, Integer.valueOf(0), Boolean.valueOf(false), null});
        }
    }

    MessagesController$91(MessagesController this$0, TLRPC$ChatFull tLRPC$ChatFull, String str) {
        this.this$0 = this$0;
        this.val$info = tLRPC$ChatFull;
        this.val$about = str;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response instanceof TLRPC$TL_boolTrue) {
            AndroidUtilities.runOnUIThread(new C15181());
        }
    }
}
