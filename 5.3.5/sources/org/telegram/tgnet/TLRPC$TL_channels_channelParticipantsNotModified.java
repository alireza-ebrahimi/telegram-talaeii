package org.telegram.tgnet;

public class TLRPC$TL_channels_channelParticipantsNotModified extends TLRPC$channels_ChannelParticipants {
    public static int constructor = -266911767;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
