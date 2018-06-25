package org.telegram.tgnet;

public class TLRPC$TL_channels_getFullChannel extends TLObject {
    public static int constructor = 141781513;
    public TLRPC$InputChannel channel;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_chatFull.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
    }
}
