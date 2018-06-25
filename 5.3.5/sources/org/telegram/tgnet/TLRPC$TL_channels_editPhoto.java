package org.telegram.tgnet;

public class TLRPC$TL_channels_editPhoto extends TLObject {
    public static int constructor = -248621111;
    public TLRPC$InputChannel channel;
    public TLRPC$InputChatPhoto photo;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        this.photo.serializeToStream(stream);
    }
}
