package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$updates_ChannelDifference extends TLObject {
    public ArrayList<Chat> chats = new ArrayList();
    public int flags;
    public boolean isFinal;
    public ArrayList<Message> messages = new ArrayList();
    public ArrayList<Message> new_messages = new ArrayList();
    public ArrayList<TLRPC$Update> other_updates = new ArrayList();
    public int pts;
    public int read_inbox_max_id;
    public int read_outbox_max_id;
    public int timeout;
    public int top_message;
    public int unread_count;
    public int unread_mentions_count;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$updates_ChannelDifference TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$updates_ChannelDifference tLRPC$updates_ChannelDifference = null;
        switch (i) {
            case 543450958:
                tLRPC$updates_ChannelDifference = new TLRPC$TL_updates_channelDifference();
                break;
            case 1041346555:
                tLRPC$updates_ChannelDifference = new TLRPC$TL_updates_channelDifferenceEmpty();
                break;
            case 1788705589:
                tLRPC$updates_ChannelDifference = new TLRPC$TL_updates_channelDifferenceTooLong();
                break;
        }
        if (tLRPC$updates_ChannelDifference == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in updates_ChannelDifference", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$updates_ChannelDifference != null) {
            tLRPC$updates_ChannelDifference.readParams(abstractSerializedData, z);
        }
        return tLRPC$updates_ChannelDifference;
    }
}
