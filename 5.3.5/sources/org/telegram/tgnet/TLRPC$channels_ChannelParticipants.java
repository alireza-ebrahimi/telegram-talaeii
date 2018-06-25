package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$channels_ChannelParticipants extends TLObject {
    public int count;
    public ArrayList<TLRPC$ChannelParticipant> participants = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$channels_ChannelParticipants TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$channels_ChannelParticipants result = null;
        switch (constructor) {
            case -266911767:
                result = new TLRPC$TL_channels_channelParticipantsNotModified();
                break;
            case -177282392:
                result = new TLRPC$TL_channels_channelParticipants();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in channels_ChannelParticipants", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
