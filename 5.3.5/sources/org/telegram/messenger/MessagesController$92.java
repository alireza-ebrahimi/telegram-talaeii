package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;

class MessagesController$92 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$chat_id;
    final /* synthetic */ String val$userName;

    /* renamed from: org.telegram.messenger.MessagesController$92$1 */
    class C15191 implements Runnable {
        C15191() {
        }

        public void run() {
            TLRPC$Chat chat = MessagesController$92.this.this$0.getChat(Integer.valueOf(MessagesController$92.this.val$chat_id));
            if (MessagesController$92.this.val$userName.length() != 0) {
                chat.flags |= 64;
            } else {
                chat.flags &= -65;
            }
            chat.username = MessagesController$92.this.val$userName;
            ArrayList<TLRPC$Chat> arrayList = new ArrayList();
            arrayList.add(chat);
            MessagesStorage.getInstance().putUsersAndChats(null, arrayList, true, true);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, new Object[]{Integer.valueOf(8192)});
        }
    }

    MessagesController$92(MessagesController this$0, int i, String str) {
        this.this$0 = this$0;
        this.val$chat_id = i;
        this.val$userName = str;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response instanceof TLRPC$TL_boolTrue) {
            AndroidUtilities.runOnUIThread(new C15191());
        }
    }
}
