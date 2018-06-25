package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$updates_ChannelDifference extends TLObject {
    public ArrayList<TLRPC$Chat> chats = new ArrayList();
    public int flags;
    public boolean isFinal;
    public ArrayList<TLRPC$Message> messages = new ArrayList();
    public ArrayList<TLRPC$Message> new_messages = new ArrayList();
    public ArrayList<TLRPC$Update> other_updates = new ArrayList();
    public int pts;
    public int read_inbox_max_id;
    public int read_outbox_max_id;
    public int timeout;
    public int top_message;
    public int unread_count;
    public int unread_mentions_count;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$updates_ChannelDifference TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$updates_ChannelDifference result = null;
        switch (constructor) {
            case 543450958:
                result = new TLRPC$TL_updates_channelDifference();
                break;
            case 1041346555:
                result = new TLRPC$TL_updates_channelDifferenceEmpty();
                break;
            case 1788705589:
                result = new TLRPC$TL_updates_channelDifferenceTooLong();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in updates_ChannelDifference", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
