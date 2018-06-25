package org.telegram.tgnet;

public class TLRPC$TL_channels_readHistory extends TLObject {
    public static int constructor = -871347913;
    public TLRPC$InputChannel channel;
    public int max_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeInt32(this.max_id);
    }
}
