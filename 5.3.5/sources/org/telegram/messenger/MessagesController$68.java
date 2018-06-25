package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_getUnreadMentions;
import org.telegram.tgnet.TLRPC$messages_Messages;

class MessagesController$68 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ ArrayList val$arrayList;

    MessagesController$68(MessagesController this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$arrayList = arrayList;
    }

    public void run() {
        for (int a = 0; a < this.val$arrayList.size(); a++) {
            final long dialog_id = (long) (-((Integer) this.val$arrayList.get(a)).intValue());
            TLRPC$TL_messages_getUnreadMentions req = new TLRPC$TL_messages_getUnreadMentions();
            req.peer = MessagesController.getInputPeer((int) dialog_id);
            req.limit = 1;
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            TLRPC$messages_Messages res = response;
                            if (res != null) {
                                int newCount;
                                if (res.count != 0) {
                                    newCount = res.count;
                                } else {
                                    newCount = res.messages.size();
                                }
                                MessagesStorage.getInstance().resetMentionsCount(dialog_id, newCount);
                            }
                        }
                    });
                }
            });
        }
    }
}
