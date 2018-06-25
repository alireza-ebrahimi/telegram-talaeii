package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$29 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ User val$user;

    MessagesController$29(MessagesController this$0, User user) {
        this.this$0 = this$0;
        this.val$user = user;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (error == null) {
            ArrayList<Integer> ids = new ArrayList();
            ids.add(Integer.valueOf(this.val$user.id));
            MessagesStorage.getInstance().putBlockedUsers(ids, false);
        }
    }
}
