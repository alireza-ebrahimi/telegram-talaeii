package org.telegram.messenger;

import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contactBlocked;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$contacts_Blocked;
import org.telegram.tgnet.TLRPC.User;

class MessagesController$33 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;

    MessagesController$33(MessagesController this$0) {
        this.this$0 = this$0;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        ArrayList<Integer> blocked = new ArrayList();
        ArrayList<User> users = null;
        if (error == null) {
            TLRPC$contacts_Blocked res = (TLRPC$contacts_Blocked) response;
            Iterator it = res.blocked.iterator();
            while (it.hasNext()) {
                blocked.add(Integer.valueOf(((TLRPC$TL_contactBlocked) it.next()).user_id));
            }
            users = res.users;
            MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
            MessagesStorage.getInstance().putBlockedUsers(blocked, true);
        }
        this.this$0.processLoadedBlockedUsers(blocked, users, false);
    }
}
