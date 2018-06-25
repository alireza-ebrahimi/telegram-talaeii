package org.telegram.tgnet;

public class TLRPC$TL_channelMessagesFilterEmpty extends TLRPC$ChannelMessagesFilter {
    public static int constructor = -1798033689;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
