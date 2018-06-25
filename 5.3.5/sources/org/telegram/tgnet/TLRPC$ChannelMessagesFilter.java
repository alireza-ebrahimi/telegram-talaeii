package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$ChannelMessagesFilter extends TLObject {
    public boolean exclude_new_messages;
    public int flags;
    public ArrayList<TLRPC$TL_messageRange> ranges = new ArrayList();

    public static TLRPC$ChannelMessagesFilter TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChannelMessagesFilter result = null;
        switch (constructor) {
            case -1798033689:
                result = new TLRPC$TL_channelMessagesFilterEmpty();
                break;
            case -847783593:
                result = new TLRPC$TL_channelMessagesFilter();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChannelMessagesFilter", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
