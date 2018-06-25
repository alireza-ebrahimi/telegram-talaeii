package org.telegram.messenger;

import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC$TL_error;

class MessagesController$48 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ Integer val$chat_id;

    MessagesController$48(MessagesController this$0, Integer num) {
        this.this$0 = this$0;
        this.val$chat_id = num;
    }

    public void run(final TLObject response, final TLRPC$TL_error error) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (error == null) {
                    TLRPC$TL_channels_channelParticipants res = response;
                    MessagesController$48.this.this$0.putUsers(res.users, false);
                    MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
                    MessagesStorage.getInstance().updateChannelUsers(MessagesController$48.this.val$chat_id.intValue(), res.participants);
                    MessagesController.access$4000(MessagesController$48.this.this$0).add(MessagesController$48.this.val$chat_id);
                }
                MessagesController.access$4100(MessagesController$48.this.this$0).remove(MessagesController$48.this.val$chat_id);
            }
        });
    }
}
