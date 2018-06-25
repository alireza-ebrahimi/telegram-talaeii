package org.telegram.tgnet;

public class TLRPC$TL_channelParticipantsAdmins extends TLRPC$ChannelParticipantsFilter {
    public static int constructor = -1268741783;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
