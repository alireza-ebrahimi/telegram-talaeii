package org.telegram.tgnet;

public class TLRPC$TL_channelParticipantsRecent extends TLRPC$ChannelParticipantsFilter {
    public static int constructor = -566281095;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
