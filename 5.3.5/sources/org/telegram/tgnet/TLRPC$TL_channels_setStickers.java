package org.telegram.tgnet;

public class TLRPC$TL_channels_setStickers extends TLObject {
    public static int constructor = -359881479;
    public TLRPC$InputChannel channel;
    public TLRPC$InputStickerSet stickerset;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        this.stickerset.serializeToStream(stream);
    }
}
