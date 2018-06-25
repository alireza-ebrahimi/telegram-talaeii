package org.telegram.tgnet;

public class TLRPC$TL_channels_editTitle extends TLObject {
    public static int constructor = 1450044624;
    public TLRPC$InputChannel channel;
    public String title;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Updates.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeString(this.title);
    }
}