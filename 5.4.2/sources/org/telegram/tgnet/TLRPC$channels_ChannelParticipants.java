package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipantsNotModified;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$channels_ChannelParticipants extends TLObject {
    public int count;
    public ArrayList<ChannelParticipant> participants = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$channels_ChannelParticipants TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$channels_ChannelParticipants tLRPC$channels_ChannelParticipants = null;
        switch (i) {
            case -266911767:
                tLRPC$channels_ChannelParticipants = new TL_channels_channelParticipantsNotModified();
                break;
            case -177282392:
                tLRPC$channels_ChannelParticipants = new TL_channels_channelParticipants();
                break;
        }
        if (tLRPC$channels_ChannelParticipants == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in channels_ChannelParticipants", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$channels_ChannelParticipants != null) {
            tLRPC$channels_ChannelParticipants.readParams(abstractSerializedData, z);
        }
        return tLRPC$channels_ChannelParticipants;
    }
}
