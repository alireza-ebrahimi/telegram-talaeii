package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$ChannelParticipant;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC$TL_error;

class MessagesController$12 implements RequestDelegate {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ int val$chatId;

    MessagesController$12(MessagesController this$0, int i) {
        this.this$0 = this$0;
        this.val$chatId = i;
    }

    public void run(TLObject response, TLRPC$TL_error error) {
        if (response instanceof TLRPC$TL_channels_channelParticipants) {
            TLRPC$TL_channels_channelParticipants participants = (TLRPC$TL_channels_channelParticipants) response;
            ArrayList<Integer> array = new ArrayList(participants.participants.size());
            for (int a = 0; a < participants.participants.size(); a++) {
                array.add(Integer.valueOf(((TLRPC$ChannelParticipant) participants.participants.get(a)).user_id));
            }
            this.this$0.processLoadedChannelAdmins(array, this.val$chatId, false);
        }
    }
}
