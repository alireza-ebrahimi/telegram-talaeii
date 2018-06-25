package org.telegram.tgnet;

public class TLRPC$TL_channels_checkUsername extends TLObject {
    public static int constructor = 283557164;
    public TLRPC$InputChannel channel;
    public String username;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeString(this.username);
    }
}
