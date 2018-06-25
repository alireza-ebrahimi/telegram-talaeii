package org.telegram.tgnet;

public class TLRPC$TL_channels_editAbout extends TLObject {
    public static int constructor = 333610782;
    public String about;
    public TLRPC$InputChannel channel;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeString(this.about);
    }
}
