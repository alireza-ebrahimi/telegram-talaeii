package org.telegram.tgnet;

public class TLRPC$TL_channelParticipantsBots extends TLRPC$ChannelParticipantsFilter {
    public static int constructor = -1328445861;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
