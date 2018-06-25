package org.telegram.tgnet;

public class TLRPC$TL_channels_exportMessageLink extends TLObject {
    public static int constructor = -934882771;
    public TLRPC$InputChannel channel;
    public int id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_exportedMessageLink.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeInt32(this.id);
    }
}
